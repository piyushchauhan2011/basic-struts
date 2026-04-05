# basic-struts

Hello World–style [Apache Struts 2](https://struts.apache.org/) (2.5.x) web app on **Java 21**, with **JUnit 5** unit tests, **Failsafe** integration tests, optional **Docker** (Tomcat 9), and **GitHub Actions** CI.

This repository is a **multi-module Maven** build:

| Module | Purpose |
|--------|---------|
| [`basic-struts`](basic-struts/) | Struts 2 WAR (main web app) |
| [`greeting-rmi-api`](greeting-rmi-api/) | Shared `RemoteGreeting` RMI interface |
| [`greeting-rmi-server`](greeting-rmi-server/) | Standalone RMI registry + server (second JVM) |
| [`greeting-ejb`](greeting-ejb/) | Stateless session bean for **EJB** learning (deploy to a Java EE server) |

## Prerequisites

- JDK **21**
- For Maven without the wrapper: Maven **3.6.3+** (enforced by the build)

## Build and test

From the **repository root**:

```bash
./mvnw verify
```

- **Unit tests:** Surefire (`*Test.java`) in [`basic-struts`](basic-struts/)
- **Integration tests:** Failsafe (`*IT.java`) in [`basic-struts`](basic-struts/)

## Run locally (Jetty)

```bash
./mvnw -pl basic-struts -am jetty:run
```

`-am` (“also make”) builds dependent reactor modules (for example `greeting-rmi-api`) so they do not need a prior `mvn install`. Without it, Maven only builds `basic-struts` and tries to download `greeting-rmi-api` from Central, which fails.

The **aggregator** [`pom.xml`](pom.xml) also declares the Jetty plugin so the `jetty:` goal prefix resolves when you run Maven from the repository root (not only from inside [`basic-struts/pom.xml`](basic-struts/pom.xml)).

Open: `http://localhost:8080/basic-struts/` (context path matches the web module artifact id).

### JVM and Struts on Java 16+

Struts 2.5’s i18n layer needs reflective access to `java.util` on modern JDKs. This repo sets:

- **[`.mvn/jvm.config`](.mvn/jvm.config)** — applies when you run Maven (`./mvnw`, etc.)
- **Surefire / Failsafe** — `jdk.test.addOpens` in [`basic-struts/pom.xml`](basic-struts/pom.xml)
- **Jetty plugin** — same flag in [`basic-struts/pom.xml`](basic-struts/pom.xml) `jetty-maven-plugin` `jvmArgs` where supported

If you run the WAR on Tomcat or another container **without** these flags passed to the JVM, add:

`--add-opens=java.base/java.util=ALL-UNNAMED`

## Learning: RMI (two JVMs)

**RMI** lets one JVM call methods on an object in another JVM via a registry and stubs. **Jetty does not host an RMI server**; this project uses a **separate process** for the registry and remote implementation.

1. **Start the RMI server** (terminal 1):

   ```bash
   ./mvnw -pl greeting-rmi-server -am compile exec:java
   ```

   `-am` includes `greeting-rmi-api` in the reactor; **`compile`** builds those modules before **`exec:java`** (running only `exec:java` does not compile siblings, so the API JAR may be missing). Without `-am`, Maven tries to download `greeting-rmi-api` from Central and fails. If you previously hit a cached “not found” error, use `./mvnw -U ...` once.

   The parent POM skips `exec:java` on itself so `-am` does not fail on the aggregator; only `greeting-rmi-server` runs the main class.

   Default registry port **1099**, bind name **GreetingService** (override with `-Dgreeting.rmi.registry.port=…` and `-Dgreeting.rmi.name=…`).

2. **Enable the web app as an RMI client** — either edit [`basic-struts/src/main/resources/greeting-rmi.properties`](basic-struts/src/main/resources/greeting-rmi.properties) and set `greeting.rmi.enabled=true`, or pass a **JVM** system property (Jetty runs in the same JVM as Maven here):

   ```bash
   MAVEN_OPTS='-Dgreeting.rmi.enabled=true' ./mvnw -pl basic-struts -am jetty:run
   ```

3. Use the app as usual. [`GreetingService`](basic-struts/src/main/java/com/example/service/GreetingService.java) looks up `RemoteGreeting` from the registry and calls `buildGreeting` remotely. If the server is not running, you’ll see a clear fallback message in the greeting text.

**CI** keeps `greeting.rmi.enabled=false` so tests stay local and do not require a second JVM.

## Learning: EJB (Java EE server)

**EJBs** run inside an **EJB container** (part of Java EE / Jakarta EE). **Servlet-only containers such as Jetty and plain Tomcat do not execute EJBs.** The [`greeting-ejb`](greeting-ejb/) module builds an **EJB-JAR** you deploy to a full EE server, for example:

- [WildFly](https://www.wildfly.org/)
- [Payara Server](https://payara.fish/)

Build the EJB artifact:

```bash
./mvnw -pl greeting-ejb package
```

Output: `greeting-ejb/target/greeting-ejb-1.0.0.jar`. Deploy that JAR using your server’s admin console or CLI. The module contains a **`@Stateless`** [`GreetingBean`](greeting-ejb/src/main/java/com/example/ejb/GreetingBean.java) with a `buildGreeting` method (same semantics as the web app’s greeting logic).

**Conceptual split:** the Struts WAR handles **web MVC**; an EJB module packages **server-side business components** for a managed environment (transactions, pooling, etc., when you extend beyond this demo).

## Maven profiles

| Profile | `struts.devMode` | Typical use |
|--------|-------------------|-------------|
| `dev` (default) | `true` | Local development |
| `prod` | `false` | Production-like builds, Docker image |

`struts.devMode` is injected via **resource filtering** into [`basic-struts/src/main/resources/struts.properties`](basic-struts/src/main/resources/struts.properties).

Example:

```bash
./mvnw -Pprod package
```

## Docker (Tomcat 9, javax / Struts 2.5)

Build from the **repository root** (Dockerfile expects the multi-module layout):

```bash
docker build -t basic-struts:local .
```

Run:

```bash
docker run --rm -p 8080:8080 basic-struts:local
```

The image deploys the WAR as **ROOT**; use `http://localhost:8080/`.

## CI

[`.github/workflows/ci.yml`](.github/workflows/ci.yml) runs `./mvnw -B verify` on pushes and pull requests to `main` / `master`.

## Security notes (demo scope)

- **GET** `hello` action: no CSRF token (simple links).
- **POST** `helloSubmit` action: Struts **token** interceptor (form in [`basic-struts/src/main/webapp/index.jsp`](basic-struts/src/main/webapp/index.jsp)).
- **Security headers** filter runs first in [`basic-struts/src/main/webapp/WEB-INF/web.xml`](basic-struts/src/main/webapp/WEB-INF/web.xml).

## Servlet API

The app targets **Servlet 4.0 / javax** ([`basic-struts/src/main/webapp/WEB-INF/web.xml`](basic-struts/src/main/webapp/WEB-INF/web.xml)), aligned with Struts 2.5. A future **Jakarta / Struts 6** migration would be a separate effort.
