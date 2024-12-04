#!/bin/bash
source .env

MIGRATIONS_DIR="./backend/migrations"

for file in $(ls $MIGRATIONS_DIR | sort); do
    APPLIED=$(docker exec -i $DB_HOST psql -U $DB_USER -d $DB_NAME -tAc "SELECT 1 FROM migration_history WHERE filename = '$file'")
    
    if [ "$APPLIED" != "1" ]; then
        echo "Applying migration: $file"
        docker exec -i $DB_HOST psql -U $DB_USER -d $DB_NAME < "$MIGRATIONS_DIR/$file"
        
        docker exec -i $DB_HOST psql -U $DB_USER -d $DB_NAME -c "INSERT INTO migration_history (filename) VALUES ('$file')"
    else
        echo "Skipping already applied migration: $file"
    fi
done

echo "All migrations applied!"
