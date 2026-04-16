# Q-M-A Microservices Startup Script

$PSScriptRoot = Get-Location

Write-Host "--- Starting Infrastructure ---" -ForegroundColor Cyan

# 1. Start MySQL via Docker (on port 3307 to avoid conflict)
Write-Host "Ensuring MySQL is running..."
docker compose up -d mysql

# 2. Start Eureka Server
Write-Host "Starting Eureka Server..."
cd "$PSScriptRoot\eureka-server"
Start-Process -NoNewWindow -FilePath ".\mvnw.cmd" -ArgumentList "spring-boot:run" -RedirectStandardOutput "$PSScriptRoot\eureka-server.log" -RedirectStandardError "$PSScriptRoot\eureka-server-error.log"

# 3. Wait for Eureka Server to be healthy
Write-Host "Waiting for Eureka Server to start on port 8761..." -NoNewline
$retries = 0
$maxRetries = 30
while ($retries -lt $maxRetries) {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8761/actuator/health" -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            Write-Host " Ready!" -ForegroundColor Green
            break
        }
    } catch {
        Write-Host "." -NoNewline
        Start-Sleep -Seconds 2
        $retries++
    }
}

if ($retries -eq $maxRetries) {
    Write-Host " Timeout! Eureka Server failed to start." -ForegroundColor Red
    exit 1
}

# 4. Start other services
$services = @("auth-service", "user-service", "measurement-service", "api-gateway")

foreach($service in $services) {
    Write-Host "Starting $service..." -ForegroundColor Cyan
    cd "$PSScriptRoot\$service"
    
    # Pass DB environment variables to services
    $env:DB_HOST = "localhost"
    $env:DB_PORT = "3307"
    $env:DB_USERNAME = "root"
    $env:DB_PASSWORD = "root"
    $env:EUREKA_HOST = "localhost"

    Start-Process -NoNewWindow -FilePath ".\mvnw.cmd" -ArgumentList "spring-boot:run" -RedirectStandardOutput "$PSScriptRoot\$service.log" -RedirectStandardError "$PSScriptRoot\$service-error.log"
    Start-Sleep -Seconds 2 # Small delay between starts
}

Write-Host "--- All services are starting ---" -ForegroundColor Green
Write-Host "Check root directory for .log and -error.log files."
cd $PSScriptRoot
