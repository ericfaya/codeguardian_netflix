import json
import subprocess
import os
import sys

def get_diff_lines(file_path):
    result = subprocess.run(
        ["git", "diff", f"origin/main...HEAD", "--", file_path],
        capture_output=True,
        text=True
    )
    return result.stdout.splitlines()

def get_line_positions(diff_lines):
    positions = {}
    position = 0
    old_line = new_line = 0

    for line in diff_lines:
        if line.startswith("@@"):
            # Parse diff header
            parts = line.split(" ")
            new_file_range = parts[2]  # like "+12,7"
            new_line = int(new_file_range.split(",")[0][1:])
            position = 0
            continue

        if line.startswith(" "):
            old_line += 1
            new_line += 1
            position += 1
        elif line.startswith("-"):
            old_line += 1
        elif line.startswith("+"):
            positions[new_line] = position
            new_line += 1
            position += 1
    return positions

def comment_on_pr(file, line, body, pr_number, repo):
    diff_lines = get_diff_lines(file)
    line_positions = get_line_positions(diff_lines)
    position = line_positions.get(line)

    if position is None:
        print(f"⚠️ No se pudo encontrar la posición diff para {file}:{line}")
        return

    print(f"💬 Comentando en {file}:{line} (posición diff: {position})")
    subprocess.run([
        "gh", "pr", "comment", str(pr_number),
        "--repo", repo,
        "--body", body,
        "--path", file,
        "--position", str(position)
    ])

def main():
    with open("groq_output.json") as f:
        data = json.load(f)

    pr_number = os.environ["PR_NUMBER"]
    repo = os.environ["GITHUB_REPOSITORY"]

    for entry in data:
        file = entry["file"]
        for comment in entry["comments"]:
            line = comment["line"]
            body = comment["body"]
            comment_on_pr(file, line, body, pr_number, repo)

if _name_ == "_main_":
    main()