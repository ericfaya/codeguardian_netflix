import json
import os
import subprocess

with open('groq_output.json', 'r') as f:
    data = json.load(f)

pr_number = os.environ['PR_NUMBER']
repo = os.environ['GITHUB_REPOSITORY']
owner, repo_name = repo.split('/')
base_branch = os.environ['BASE_BRANCH']

# Obtenemos el SHA del último commit en el PR
def get_latest_commit_sha():
    sha = subprocess.check_output(['git', 'rev-parse', 'HEAD']).decode('utf-8').strip()
    return sha

commit_sha = get_latest_commit_sha()

for file_entry in data:
    path = file_entry['file']
    for comment in file_entry['comments']:
        line = comment['line']
        body = comment['body']

        print(f"💬 Comentando en {path} línea {line}: {body}")

        subprocess.run([
            'gh', 'api',
            '-X', 'POST',
            '-H', 'Accept: application/vnd.github+json',
            f'/repos/{owner}/{repo_name}/pulls/{pr_number}/comments',
            '-f', f'body={body}',
            '-f', f'commit_id={commit_sha}',
            '-f', f'path={path}',
            '-f', f'line={line}',
            '-f', 'side=RIGHT'
        ])