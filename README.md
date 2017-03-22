# Aria2FTP

The program is used to download (using aria2) a file from a ftp server against some queries, it is done by periodically polling the server.

## How to run

1. Pack it into a jar, `sbt assembly`
2. Launch the program by providing a config file, `java -jar Aria2P.jar -c ./config.xml`, alternatively use `run.bat`

## Config file

```yaml
user: <user to ftp>
password: <password  to ftp>
aria2_path: <path to aria2c>
aria2_args: <paramters to aria2c>(-x10 -j10 -s10)
ftp_url: <ftp url>(ftp://xxx.xxx)
query: <query on ftp dir>
output: <output path>
interval: <run interval>(0min)
```

A sample config file (`config_example.yml`) is also given