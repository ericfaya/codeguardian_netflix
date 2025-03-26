import json
import subprocess
import os
import sys

PR_NUMBER = os.environ.get("PR_NUMBER")
BASE_BRANCH = os.environ.get("BASE_BRANCH", "main")

if not PR_NUMBER:
    print("❌ No PR_NUMBER definido.")
    sys.exit(1)

json_file = "groq_output.json"

try:
    with open(json_file) as f:
        data = json.load(f)
except FileNotFoundError:
    print(f"❌ No se encontró {json_file}")
    sys.exit(1)

def get_diff_position(file_path, target_line):
    result = subprocess.run(
        ["git", "diff", f"origin/{BASE_BRANCH}...HEAD", "--", file_path],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True
    )
    lines = result.stdout.splitlines()
    current_line = 0
    position = None

    for i, line in enumerate(lines):
        if line.startswith("@@"):
            parts = line.split(" ")
            if len(parts) > 2:
                new_range = parts[2]
                if "," in new_range:
                    start = int(new_range[1:].split(",")[0])
                else:
                    start = int(new_range[1:])
                current_line = start
        elif line.startswith("+") and not line.startswith("+++"):
            if current_line == target_line:
                position = i + 1  # 1-based diff index
                break
            current_line += 1
        elif not line.startswith("-"):
            current_line += 1

    return position

for file_entry in data:
    file_path = file_entry["file"]
    for comment in file_entry["comments"]:
        line = comment["line"]
        body = comment["body"]
        pos = get_diff_position(file_path, line)

        if pos is None:
            print(f"⚠️  No se encontró posición en el diff para {file_path}:{line}")
            continue

        print(f"💬 Comentario en {file_path}:{line} (posición {pos})")
        subprocess.run([
            "gh", "pr", "comment", str(PR_NUMBER),
            "--body", body,
            "--path", file_path,
            "--position", str(pos)
        ])