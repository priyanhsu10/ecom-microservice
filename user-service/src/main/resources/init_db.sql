-- Create a new user for the application
CREATE USER ecom_user WITH PASSWORD 'ecom_password';

-- Create the database
CREATE DATABASE ecom_db;

-- Grant privileges to the user
GRANT ALL PRIVILEGES ON DATABASE ecom_db TO ecom_user;

-- Connect to the ecom_db
\c ecom_db;

-- Grant schema permissions
GRANT ALL ON SCHEMA public TO ecom_user;

-- Grant permissions for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO ecom_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO ecom_user;
