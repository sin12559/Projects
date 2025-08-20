import yaml
import click
import subprocess
import os
import requests
import psutil
from rich.console import Console

console = Console()

def run_shell(command):
    try:
        subprocess.check_output(command, shell=True)
        return True, f"Command succeeded: {command}"
    except subprocess.CalledProcessError as e:
        return False, f"Command failed: {command}\n{e}"

def check_http(url):
    try:
        r = requests.get(url, timeout=5)
        return r.status_code == 200, f"HTTP {r.status_code} {url}"
    except Exception as e:
        return False, f"HTTP check failed: {url} ({e})"

def check_file(path):
    return os.path.exists(path), f"File exists: {path}"

def check_disk(threshold):
    usage = psutil.disk_usage('/')
    percent = usage.percent
    return percent < threshold, f"Disk usage {percent}% (threshold {threshold}%)"

@click.group()
def cli():
    pass

@cli.command()
@click.argument('config')
@click.option('--format', default='text', type=click.Choice(['text', 'json']))
def run(config, format):
    """Run checklist from YAML config"""
    with open(config) as f:
        checklist = yaml.safe_load(f)

    results = []
    for item in checklist['checks']:
        ctype = item['type']
        if ctype == 'shell':
            ok, msg = run_shell(item['command'])
        elif ctype == 'http':
            ok, msg = check_http(item['url'])
        elif ctype == 'file':
            ok, msg = check_file(item['path'])
        elif ctype == 'disk':
            ok, msg = check_disk(item['threshold'])
        else:
            ok, msg = False, f"Unknown check type: {ctype}"

        results.append({"name": item['name'], "ok": ok, "msg": msg})

    success = all(r['ok'] for r in results)

    if format == 'text':
        for r in results:
            if r['ok']:
                console.print(f"[green]✔ {r['name']}[/green] - {r['msg']}")
            else:
                console.print(f"[red]✘ {r['name']}[/red] - {r['msg']}")
    else:
        import json
        console.print_json(data=results)

    raise SystemExit(0 if success else 1)

if __name__ == '__main__':
    cli()
