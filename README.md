### Build
`docker build -t orttest .`
### Execute
Run 
`docker run orttest --args="/app/NewGradleVersion"` to execute the ORT on a gradle project version 7.2, which produces a `IllegalArgumentException: Unsupported class file major version 65]}` error.

Run `docker run orttest --args="/app/NewGradleVersion"` to execute the ORT on a gradle 8.5 project which doesn't produce the error.

### Test repositories
The used test repositories can be found at https://github.com/janniclas/NewGradleVersion and https://github.com/janniclas/OldGradleVersion.
