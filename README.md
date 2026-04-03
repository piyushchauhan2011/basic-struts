# basic-struts

Hello World–style [Apache Struts 2](https://struts.apache.org/) (2.5.x) web app on **Java 21**, with **JUnit 5** unit tests, **Failsafe** integration tests, optional **Docker** (Tomcat 9), and **GitHub Actions** CI.

## Prerequisites

- JDK **21**
- For Maven without the wrapper: Maven **3.6.3+** (enforced by the build)

## Build and test

```bash
./mvnw verify
```

- **Unit tests:** Surefire (`*Test.java`)
- **Integration tests:** Failsafe (`*IT.java`)

## Run locally (Jetty)

```bash
./mvnw jetty:run
```

Open: `http://localhost:8080/basic-struts/` (context path matches the artifact id).

### JVM and Struts on Java 16+

Struts 2.5’s i18n layer needs reflective access to `java.util` on modern JDKs. This repo sets:

- **[`.mvn/jvm.config`](.mvn/jvm.config)** — applies when you run Maven (`./mvnw jetty:run`, `./mvnw verify`, etc.)
- **Surefire / Failsafe** — [`jdk.test.addOpens`](pom.xml) in the POM
- **Jetty plugin** — same flag in [`pom.xml`](pom.xml) `jetty-maven-plugin` `jvmArgs` where supported

If you run the WAR on Tomcat or another container **without** these flags passed to the JVM, add:

`--add-opens=java.base/java.util=ALL-UNNAMED`

## Maven profiles

| Profile | `struts.devMode` | Typical use |
|--------|-------------------|-------------|
| `dev` (default) | `true` | Local development |
| `prod` | `false` | Production-like builds, Docker image |

`struts.devMode` is injected via **resource filtering** into [`src/main/resources/struts.properties`](src/main/resources/struts.properties).

Example:

```bash
./mvnw -Pprod package
```

## Docker (Tomcat 9, javax / Struts 2.5)

Build:

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
- **POST** `helloSubmit` action: Struts **token** interceptor (form in [`index.jsp`](src/main/webapp/index.jsp)).
- **Security headers** filter runs first in [`web.xml`](src/main/webapp/WEB-INF/web.xml).

## Servlet API

The app targets **Servlet 4.0 / javax** ([`web.xml`](src/main/webapp/WEB-INF/web.xml)), aligned with Struts 2.5. A future **Jakarta / Struts 6** migration would be a separate effort.
