import json
import subprocess
import os
import sys

def get_diff_lines(file_path, base_branch):
    result = subprocess.run(
        ["git", "diff", f"origin/{base_branch}...HEAD", "--", file_path],
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

def comment_on_pr(file, line, body, pr_number, repo, base_branch):
    diff_lines = get_diff_lines(file, base_branch)
    line_positions = get_line_positions(diff_lines)
    position = line_positions.get(line)

    if position is None:
        print(f"⚠️ No se pudo encontrar la posición diff para {file}:{line}")
        return

    print(f"💬 Comentando en {file}:{line} (posición diff: {position})")
    subprocess.run([
        "gh", "pr", "review", str(pr_number),
        "--repo", repo,
        "--comment",
        "--body", body,
        "--filename", file,
        "--line", str(line)
    ])

def main():
    try:
        with open("groq_output.json") as f:
            data = json.load(f)
    except Exception as e:
        print(f"❌ Error al cargar groq_output.json: {e}")
        sys.exit(1)

    pr_number = os.environ["PR_NUMBER"]
    repo = os.environ["GITHUB_REPOSITORY"]
    base_branch = os.environ.get("BASE_BRANCH", "main")

    for entry in data:
        file = entry["file"]
        for comment in entry["comments"]:
            line = comment["line"]
            body = comment["body"]
            comment_on_pr(file, line, body, pr_number, repo, base_branch)

if __name__ == "__main__":
    main()