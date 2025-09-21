#!/usr/bin/env bash
set -euo pipefail
# kill anything on 8080
lsof -ti:8080 2>/dev/null | xargs -r kill -9 || true
# choose tool
CMD=""
if [[ -x ./mvnw ]]; then CMD="./mvnw -q -DskipTests spring-boot:run"
elif command -v mvn >/dev/null 2>&1; then CMD="mvn -q -DskipTests spring-boot:run"
elif [[ -x ./gradlew ]]; then CMD="./gradlew -q bootRun"
else CMD="gradle -q bootRun"; fi

# start & wait
rm -f .run.log
nohup bash -lc "$CMD" &> .run.log & disown

for i in {1..60}; do
  if grep -q "Tomcat started on port(s): 8080" .run.log 2>/dev/null; then
    echo "✅ App up on http://localhost:8080"
    exit 0
  fi
  sleep 1
done

echo "❌ App did not report startup. Last 80 lines:"
tail -n 80 .run.log
exit 1
