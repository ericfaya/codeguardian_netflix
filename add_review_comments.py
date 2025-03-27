import json
import os
import subprocess

# Cargar los datos del archivo JSON
with open('groq_output.json', 'r') as f:
    data = json.load(f)

print(json.dumps(data, indent=2))

if not isinstance(data, list):
    raise ValueError("El archivo JSON no tiene el formato esperado. Se esperaba una lista.")

# Obtener las variables de entorno necesarias
pr_number = os.environ.get('PR_NUMBER')
repo = os.environ.get('GITHUB_REPOSITORY')
base_branch = os.environ.get('BASE_BRANCH')

# Verificar que todas las variables de entorno estén presentes
if not pr_number or not repo or not base_branch:
    raise ValueError("Faltan variables de entorno: PR_NUMBER, GITHUB_REPOSITORY o BASE_BRANCH.")

owner, repo_name = repo.split('/')

# Función para obtener el commit SHA
def get_latest_commit_sha():
    sha = subprocess.check_output(['git', 'rev-parse', 'HEAD']).decode('utf-8').strip()
    if not sha:
        raise ValueError("No se pudo obtener el commit SHA.")
    return sha

commit_sha = get_latest_commit_sha()

# Validación para los comentarios
def validate_comment_data(comment):
    print(f"Validando comentario: {comment}")
    # Validar que 'line' sea un número entero
    if not isinstance(comment['line'], int):
        raise ValueError(f"Línea no válida: {comment['line']}. Debe ser un entero.")
    
    # Validar que 'body' no esté vacío
    if not comment['body']:
        raise ValueError(f"Comentario vacío en la línea {comment['line']}.")

    # Validar que 'path' no esté vacío
    if not comment.get('path'):
        raise ValueError(f"El archivo no está especificado para el comentario en la línea {comment['line']}.")

# Realizar los comentarios
for file_entry in data:
    print(f"Revisando archivo: {file_entry}")
    path = file_entry.get('file', None)
    if not path:
        print(f"Error: 'file' no encontrado en la entrada {file_entry}")
        continue

    for comment in file_entry['comments']:
        try:
            validate_comment_data(comment)  # Validar datos del comentario
            line = comment['line']
            body = comment['body']

            print(f"💬 Comentando en {path} línea {line}: {body}")

            # Realizar el comentario en GitHub
            result = subprocess.run([
                'gh', 'api',
                '-X', 'POST',
                '-H', 'Accept: application/vnd.github+json',
                f'/repos/{owner}/{repo_name}/pulls/{pr_number}/comments',
                '-f', f'body={body}',
                '-f', f'commit_id={commit_sha}',
                '-f', f'path={path}',
                '-f', f'line={line}',
                '-f', 'side=RIGHT'
            ], capture_output=True, text=True)
            if result.returncode != 0:
                print(f"Error en la API de github: {result.stderr}")
                raise ValueError(f"Hubo un error al public el comentario en GitHub.")

            # Imprimir la respuesta de la API para verificar si todo fue exitoso
            print("Respuesta de la API:")
            print(result.stdout)
            print(result.stderr)

        except ValueError as e:
            print(f"Error al comentar: {e}")  # Mostrar errores de validación