# java-kadmin-client
Java Kadmin makes interaction with kadmin from java apps easier.

## Install
* Use `java-kadmin-template` for your application (or make sure `kadmin` executable is available)
* Add `java-kadmin-client` dependency to your project:
```xml
<dependency>
    <groupId>io.github.vyaslav</groupId>
    <artifactId>java-kadmin-client</artifactId>
    <version>0.9</version>
</dependency>
```
## Usage
* Construct `KadminCommandsRunner` with admin principal, its keytab and krb5.conf location
* Use `KadminCommandsRunner::runCommand` to run kadmin commands available at `io/github/vyaslav/kadmin/commands`