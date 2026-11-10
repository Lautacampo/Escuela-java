# Sistema de Gestión Académica — Colegio para Adultos N.º 3 «Tomás Baras»

**Guía de instalación, configuración, ejecución y pruebas para la cátedra de Ingeniería de Software II**  
**Actualizada:** 9 de noviembre de 2026  
**Entorno explicado:** Windows 10/11, aplicación local en una sola computadora.

> **Objetivo:** permitir que una persona que no desarrolló el proyecto pueda instalar las herramientas, restaurar los datos de demostración, iniciar el backend Java y el frontend React, ingresar al sistema y comprobar sus casos de uso.
>
> **Importante para quien prepara la entrega:** esta guía depende de recibir una **copia de PostgreSQL actual, completa y anonimizada**, además de una **cuenta de demostración válida**. Los archivos revisados todavía no contienen ese respaldo completo ni un archivo `application.properties` de Java. El archivo `database/schema.sql` de React es un **esquema anterior sin datos**, no sustituye el respaldo exigido en esta guía. No afirmar que el proyecto arranca desde cero hasta probarlo en otra computadora.

---

## 1. ¿Qué aplicaciones componen el proyecto?

| Parte                                    | Tecnología                                                       | Función                                              | Puerto local |
| ---------------------------------------- | ---------------------------------------------------------------- | ---------------------------------------------------- | ------------ |
| Interfaz gráfica                         | React 19.2.8 + Vite 8.2.2                                        | Pantallas, menús, consultas, formularios             | `5173`       |
| Backend **de Ingeniería de Software II** | Java, Spring Boot 3.5.0, Maven, JPA y Spring Security            | API REST, lógica de negocio, autenticación JWT, DAO  | `8080`       |
| Base de datos                            | PostgreSQL (se recomienda la rama 18 para reproducir el entorno) | Usuarios, alumnos, materias, cursadas y evaluaciones | `5432`       |

El frontend fue compartido con el proyecto de Laboratorio de Tecnologías, **pero su backend Node.js es independiente y NO se necesita para ejecutar los casos de uso de Ingeniería de Software II**. No ejecutar la carpeta `backend` de Node.js ni el comando `npm run server` para esta demostración.

### Versiones comprobadas en los archivos entregados

- **Java del proyecto:** el archivo `pom.xml` declara `<java.version>17</java.version>`. Instalar un **JDK 17 o superior compatible**; para reducir diferencias, se recomienda **JDK 17**. La versión del JDK instalada en la computadora del equipo no se puede verificar solamente desde el código fuente.
- **Spring Boot:** `3.5.0`, declarada en `pom.xml`.
- **Maven:** el proyecto utiliza Maven y **no incluye Maven Wrapper** (`mvnw`). Recomendación: instalar Maven 3.9 o una versión compatible vigente y comprobarlo con `mvn -version`. NetBeans también puede utilizar su Maven integrado.
- **React:** dependencia `^19.2.8` en `frontend/package.json`.
- **Vite:** dependencia `^8.2.2` en `frontend/package.json`.
- **Node.js / npm usados según el README existente del frontend:** Node.js `v24.20.0` y npm `11.19.0`. La rama Node 24 LTS es compatible con Vite 8. Se recomienda usar Node 24 LTS, con una versión igual o posterior compatible.
- **PostgreSQL:** el archivo `database/schema.sql` fue exportado con PostgreSQL `18.6`, pero **no es el respaldo definitivo de Java**.
- **NetBeans:** puede usarse para abrir el Maven de Java; no es obligatorio si se compila con `mvn` desde la terminal. Se utilizó NetBeans en el desarrollo, pero el número de versión del IDE no figura en el código.
- **Visual Studio Code:** opcional para abrir el frontend; los comandos funcionan desde PowerShell.

> No es necesario buscar «la última versión» de todas las herramientas: para reproducir un proyecto es más seguro utilizar las versiones declaradas y compatibles que actualizar dependencias sin probarlas. **No ejecutar `npm update` ni cambiar la versión de Spring Boot antes de la evaluación.**

### Descargas oficiales

- JDK 17 (Temurin): <https://adoptium.net/temurin/releases/?version=17>
- Apache Maven: <https://maven.apache.org/download.cgi>
- Apache NetBeans: <https://netbeans.apache.org/front/main/download/>
- Node.js LTS: <https://nodejs.org/es/download/>
- PostgreSQL para Windows (instalador con pgAdmin): <https://www.postgresql.org/download/windows/>
- Visual Studio Code: <https://code.visualstudio.com/>
- 7-Zip (para abrir ZIP/RAR): <https://www.7-zip.org/>

---

## 2. Estructura a recibir

Extraer los archivos en una carpeta sencilla, sin caracteres especiales si es posible. Ejemplo:

```text
C:\Entrega_ISII\
│
├── README.md                              ← esta guía
├── Escuela-java-main\                     ← Spring Boot / Maven
│   ├── pom.xml
│   └── src\main\java\...
│
├── Escuela-main\                          ← proyecto web compartido
│   └── frontend\
│       ├── package.json
│       ├── package-lock.json
│       ├── src\
│       └── ...
│
├── base_datos\
│   └── colegio_demo.backup                ← DEBE APORTARLO EL EQUIPO
│
└── entregables\
    ├── ColegioTomasBaras-1.0-SNAPSHOT.jar ← ejecutable Java (opcional si se compila allí)
    └── informe-final.pdf
```

La carpeta del frontend dentro del RAR original está en:

```text
Proyecto Final - Tomas Baras\Escuela-main\frontend
```

Por eso, después de extraer, **ubicar la carpeta que contiene `package.json`**. No instalar dependencias en `Escuela-main\backend`, que pertenece a la otra asignatura.

Se debe entregar, por separado y mediante un medio seguro, una **cuenta de prueba** (correo, contraseña y rol). No poner contraseñas reales de la institución dentro de este README.

---

## 3. Preparación inicial de Windows

### 3.1. Instalar Java (JDK)

1. Descargar **Temurin JDK 17 para Windows x64** e instalarlo (no instalar solamente JRE).
2. Si el instalador lo permite, activar la configuración de `JAVA_HOME` y la incorporación de Java al `PATH`.
3. Abrir una nueva ventana de **PowerShell** (Inicio → escribir «PowerShell»).
4. Ejecutar:

```powershell
java -version
javac -version
```

Ambos comandos deben responder con una versión compatible (por ejemplo, `17.x`). Si `javac` no existe, probablemente se instaló solamente un entorno de ejecución o falta configurar el `PATH`.

En Windows, se pueden modificar variables en **Sistema → Configuración avanzada del sistema → Variables de entorno**: definir `JAVA_HOME` apuntando a la carpeta del JDK (no a `bin`) e incorporar `%JAVA_HOME%\bin` al `Path`. Cerrar y volver a abrir la terminal después de cambiarlas.

### 3.2. Instalar Maven (para compilar desde consola)

1. Descargar Maven en formato ZIP desde su página oficial.
2. Extraerlo, por ejemplo, en `C:\Herramientas\apache-maven-...`.
3. Agregar su subcarpeta `bin` al `Path` de Windows.
4. Abrir una terminal nueva y ejecutar:

```powershell
mvn -version
```

Debe mostrar Maven y la versión del JDK. Si `mvn` no se reconoce, agregar el directorio correcto al `Path` o compilar el proyecto directamente desde NetBeans.

### 3.3. Instalar Node.js y npm

1. Instalar **Node.js 24 LTS para Windows** desde el sitio oficial. npm viene incluido.
2. Abrir otra terminal nueva y ejecutar:

```powershell
node --version
npm --version
```

El equipo desarrollador indicó Node `v24.20.0` y npm `11.19.0`. Pueden aparecer versiones posteriores compatibles de la misma rama. **No crear otro proyecto Vite**: el frontend ya existe.

### 3.4. Instalar PostgreSQL y pgAdmin

1. Descargar PostgreSQL para Windows desde el enlace oficial; preferiblemente **PostgreSQL 18**, compatible con el entorno documentado.
2. Durante la instalación, seleccionar PostgreSQL Server y pgAdmin, y mantener el puerto `5432` si está disponible.
3. Definir una contraseña local para el usuario administrador `postgres` y **conservarla** para la configuración posterior.
4. Al terminar, verificar en **Servicios** de Windows que PostgreSQL esté iniciado.
5. Abrir pgAdmin, establecer su contraseña maestra si la solicita y conectarse al servidor PostgreSQL local.

Si se usa la terminal de PostgreSQL, el programa `psql.exe` suele encontrarse en `C:\Program Files\PostgreSQL\18\bin`. No es obligatorio agregar ese directorio al `PATH`: se puede utilizar la ruta completa indicada más adelante.

---

## 4. Restaurar la base de datos (paso indispensable)


### Opción A. Restauración desde pgAdmin (recomendada para quien no usa terminal)

1. Abrir **pgAdmin** y conectar el servidor local.
2. Clic derecho en **Databases → Create → Database...**.
3. Introducir el nombre `colegio_demo`, elegir propietario `postgres` (o un usuario con permisos) y guardar.
4. Clic derecho en `colegio_demo` → **Restore...**.
5. Seleccionar el archivo `C:\Entrega_ISII\base_datos\colegio_demo.backup`.
6. Elegir el formato _Custom_ si corresponde. Si el respaldo proviene de otro usuario de PostgreSQL, activar las opciones equivalentes a **No owner** y **No privileges**, si están disponibles.
7. Ejecutar la restauración y comprobar que finalice sin errores.
8. Abrir **Schemas → public → Tables** y verificar que existan las tablas utilizadas por el sistema, en particular `usuarios`, `roles`, `alumno`, `materia`, `curso` y `cursada_modulo`.

### Opción B. Restauración con comandos PowerShell

Ejecutar en PowerShell (se pedirá la contraseña del usuario `postgres`). Adaptar `18` si se instaló otra versión:

```powershell
& "C:\Program Files\PostgreSQL\18\bin\createdb.exe" `
  -h localhost -p 5432 -U postgres -E UTF8 colegio_demo
```

Después restaurar:

```powershell
& "C:\Program Files\PostgreSQL\18\bin\pg_restore.exe" `
  -h localhost -p 5432 -U postgres -d colegio_demo `
  --no-owner --no-privileges --exit-on-error `
  "C:\Entrega_ISII\base_datos\colegio_demo.backup"
```

> Si la base `colegio_demo` ya existe y contiene datos, no restaurar encima sin revisar: podría crear tablas duplicadas o conflictos. Restaurar sobre una base de demostración nueva y vacía.

### Comprobar la conexión

```powershell
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" `
  -h localhost -p 5432 -U postgres -d colegio_demo `
  -c "SELECT current_database(), current_user;"
```

También se puede comprobar desde **pgAdmin → Query Tool** ejecutando:

```sql
SELECT current_database(), current_user;
SELECT COUNT(*) FROM roles;
SELECT COUNT(*) FROM usuarios;
```

**Importante:** para poder iniciar sesión debe existir en el respaldo **al menos un usuario activo con rol `directivo` y contraseña almacenada con BCrypt**. La clave de acceso a la aplicación se proporciona por separado; **no es necesariamente la contraseña del usuario `postgres`**.

---

## 5. Configurar Spring Boot y la conexión a PostgreSQL

### 5.1. Crear el archivo `application.properties`

En el backend Java, ubicar:

```text
Escuela-java-main\src\main\resources\
```

Crear allí un archivo llamado exactamente `application.properties` (sin `.txt` al final). El RAR Java revisado **no lo incluye**. Copiar el contenido:

```properties
spring.application.name=ColegioTomasBaras
server.port=8080

# Base restaurada en el paso 4
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/colegio_demo}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

# Validar las tablas de la base que acompaña la entrega;
# no crearlas o modificarlas silenciosamente.
spring.jpa.hibernate.ddl-auto=validate
spring.sql.init.mode=never
spring.jpa.show-sql=false
```

El archivo plantilla `application.properties.example`, que acompaña a esta guía, contiene el mismo texto. **No colocar la contraseña real dentro del código ni dentro del ZIP público.** El valor `${DB_PASSWORD}` se obtiene de una variable de entorno de Windows.

Si el proyecto muestra un error `Schema-validation`, el respaldo no corresponde exactamente al modelo JPA actual. **No solucionarlo automáticamente con `ddl-auto=create` o `create-drop`**, porque esos modos pueden destruir los datos restaurados. El equipo debe revisar y entregar una base compatible y ya probada.

### 5.2. Configurar variables de entorno para esta ejecución

En la **misma ventana PowerShell desde la cual se iniciará Java**, ejecutar:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/colegio_demo"
$env:DB_USER = "postgres"
$env:DB_PASSWORD = "ESCRIBIR_AQUI_LA_CLAVE_LOCAL_DE_POSTGRES"
```

Sustituir el texto de ejemplo por la contraseña local elegida al instalar PostgreSQL. **No copiar ese valor a capturas de pantalla ni compartirlo**. Las variables asignadas con `$env:` duran solamente en esa sesión de PowerShell.

### 5.3. Generar una clave JWT de demostración

El backend utiliza tokens JWT firmados. Es recomendable **reemplazar la clave de prueba incluida como valor por defecto en `JwtConfig.java`**, usando una clave aleatoria para el equipo donde se ejecuta la demostración.

En esa **misma PowerShell**, ejecutar:

```powershell
$bytes = New-Object byte[] 48
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
$rng.Dispose()
$env:APP_JWT_SECRET = [Convert]::ToBase64String($bytes)
```

El backend utilizará esa clave para firmar y verificar los JWT. Cuando se reinicie el proceso con otra clave, las sesiones anteriores dejarán de ser válidas y habrá que volver a iniciar sesión. **No modificar manualmente los tokens ni colocar la clave en React**.

> Si se prefiere ejecutar desde el botón _Run_ de NetBeans, las variables de entorno deben estar disponibles para **el proceso de NetBeans**. Una solución práctica es configurar las variables del usuario en Windows y reiniciar NetBeans, o ejecutar el backend desde la PowerShell del paso siguiente. NetBeans no es obligatorio para correr el sistema.

---

## 6. Ejecutar el backend Java / Spring Boot

### Opción A. Desde PowerShell (más reproducible)

Abrir la terminal que contiene las variables de entorno del paso 5:

```powershell
cd "C:\Entrega_ISII\Escuela-java-main"
mvn clean package
```

La primera compilación puede tardar porque Maven debe descargar dependencias desde internet. Se espera un resultado de compilación exitoso, sin errores.

A continuación:

```powershell
mvn spring-boot:run
```

**No cerrar esta terminal.** Si el arranque es correcto, Spring Boot iniciará Tomcat en el puerto **8080**, conectará con PostgreSQL y quedará esperando solicitudes HTTP.

### Opción B. Ejecutar el JAR generado

Si el código ya se compiló o el grupo entregó el ejecutable Java, ejecutar, con las mismas variables de entorno configuradas:

```powershell
cd "C:\Entrega_ISII\Escuela-java-main"
java -jar ".\target\ColegioTomasBaras-1.0-SNAPSHOT.jar"
```

Si se entregó el JAR dentro de `entregables`, usar esa ruta en lugar de `target`. El nombre procede de `artifactId` y `version` del `pom.xml`.

### Opción C. Abrir el proyecto en NetBeans

1. Instalar y abrir Apache NetBeans con un JDK compatible.
2. Seleccionar **File → Open Project**.
3. Abrir `C:\Entrega_ISII\Escuela-java-main` (es la carpeta con `pom.xml`).
4. Esperar a que Maven descargue las dependencias y termine de indexar el proyecto.
5. Seleccionar el proyecto → **Clean and Build**.
6. Ejecutarlo solo si NetBeans dispone de las variables de entorno de PostgreSQL y JWT; de lo contrario, iniciarlo desde PowerShell como en la opción A.

### Verificación rápida del servidor

En una **segunda terminal**, sin cerrar Spring Boot:

```powershell
curl.exe -i http://localhost:8080/api/alumnos
```

Una respuesta **HTTP 401** es **esperada** al no enviar JWT, y confirma que el servidor responde y protege ese endpoint. No significa que la API esté caída. Si no hay respuesta porque no se puede conectar, revisar el arranque de Spring Boot.

---

## 7. Ejecutar el frontend React / Vite

1. Abrir una **nueva ventana PowerShell o una terminal de Visual Studio Code**.
2. Ir a la carpeta del frontend, donde está `package.json`:

```powershell
cd "C:\Entrega_ISII\Escuela-main\frontend"
```

3. Instalar exactamente las dependencias bloqueadas en `package-lock.json`:

```powershell
npm ci --include=dev
```

Si `npm ci` indica que `package.json` y `package-lock.json` no coinciden, comunicarlo al equipo: deberá generar y entregar un lockfile actualizado y comprobado. Como alternativa de desarrollo se puede probar `npm install --include=dev`, pero eso puede modificar el lockfile y no es lo ideal para reproducir la entrega.

4. Ejecutar Vite en el puerto previsto por la seguridad Java:

```powershell
npm run dev -- --host localhost --port 5173 --strictPort
```

5. Mantener esta segunda terminal abierta y abrir en el navegador:

**<http://localhost:5173/>**

La dirección **debe mantenerse en `localhost:5173`**, porque `SeguridadConfig.java` permite actualmente ese origen en CORS. Si Vite inicia en `5174`, hay que liberar el puerto `5173` o actualizar la configuración CORS en Java y reiniciarlo. No es suficiente cambiar solamente el navegador.

**No ejecutar `npm run server`**: ese comando inicia un JSON Server de pruebas, no el backend Spring Boot de Ingeniería de Software II.

**No ejecutar `npm start`**: este proyecto utiliza `npm run dev`.

---

## 8. Iniciar sesión

1. Asegurarse de que **PostgreSQL**, **Spring Boot** y **Vite** estén ejecutándose.
2. Ingresar a <http://localhost:5173/> y abrir la pantalla de inicio de sesión.
3. Introducir el **correo y la contraseña de demostración facilitados por el grupo**.
4. Presionar **«Ingresar al sistema»**.
5. Para recorrer la totalidad de los casos de uso, se recomienda usar una cuenta con rol **Directivo**. Los permisos de un usuario Docente son distintos.

La interfaz envía las credenciales a `POST http://localhost:8080/api/auth/login`. Spring Boot verifica el usuario en PostgreSQL y, si corresponde, devuelve un JWT. React utiliza ese token en el encabezado `Authorization: Bearer ...` para las solicitudes protegidas.

El proyecto **no cuenta con un usuario inicial garantizado por el código fuente**. Por eso el respaldo debe incluir las cuentas de demostración o el equipo debe suministrar un procedimiento seguro para crearlas.

---

## 9. Recorrido sugerido de demostración (casos de uso)

Con sesión de Directivo abierta, comprobar los tres casos de uso de la entrega final:

| Caso de uso                  | Recorrido en React                                                                         | Solicitudes Java principales                                                                                                      |
| ---------------------------- | ------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------- |
| **Consultar Alumno**         | Gestión de Alumnos → seleccionar **«Ver ficha»**                                           | `GET /api/alumnos/{id}`                                                                                                           |
| **Consultar Materia**        | Menú lateral **«Consultar Materia»** → **«Elegí el año»** → **«Elegí la materia»**         | `GET /api/materias` y `GET /api/materias/{id}?anio=N`                                                                             |
| **Registrar Calificaciones** | Sección **Calificaciones** → elegir módulo y división → modificar el estado de una materia | `GET /api/calificaciones/modulos`, `GET /api/calificaciones/modulos/{moduloId}/divisiones/{division}` y `PUT /api/calificaciones` |

Para mostrar los casos de uso de la segunda etapa, también es posible:

- **Registrar Alumno:** Gestión de Alumnos → **«Registrar alumno»** → completar el formulario.
- **Matricular Alumno:** al registrar un alumno, React solicita automáticamente la matrícula al curso y módulo seleccionados; el backend es `POST /api/alumnos/{idAlumno}/matricular`.
- **Calificar Módulo:** Gestión de Alumnos → **«Ver ficha»** → sección **«Calificar módulo actual»** → cargar nota/fecha → **«Guardar calificación»**; el backend es `POST /api/evaluaciones-modulo`.

> Para que la demostración sea reproducible, el equipo debería entregar una base con alumnos, materias, cursos, módulos, evaluaciones y asignaciones **ficticios o anonimizados**. Las operaciones de escritura (registro de alumnos, calificaciones y matrícula) **modifican** la base de demostración. Si se desea comenzar otra vez desde el estado original, se debe restaurar una copia limpia del respaldo.

---

## 10. Checklist de funcionamiento

Antes de comenzar la exposición, verificar:

- [ ] `java -version` y `javac -version` muestran un JDK compatible.
- [ ] `mvn -version` funciona (o NetBeans permite compilar).
- [ ] `node --version` y `npm --version` funcionan.
- [ ] PostgreSQL responde en `localhost:5432`.
- [ ] `colegio_demo` fue restaurada con **tablas y datos**.
- [ ] Existe una cuenta de demostración activa y se conoce su contraseña.
- [ ] La variable `APP_JWT_SECRET` está definida para Spring Boot.
- [ ] La variable `DB_PASSWORD` contiene la contraseña local correcta.
- [ ] El backend Java responde en `http://localhost:8080`.
- [ ] Vite responde en `http://localhost:5173`.
- [ ] Se puede iniciar sesión como Directivo.
- [ ] Consultar Alumno abre una ficha de un alumno de prueba.
- [ ] Consultar Materia muestra información real de la base restaurada.
- [ ] Registrar Calificaciones puede consultar la grilla y guardar un estado de prueba.
- [ ] Se ha probado cerrar sesión e ingresar nuevamente.

---

## 11. Problemas frecuentes y soluciones

| Problema                                           | Causa probable                                     | Qué revisar                                                                           |
| -------------------------------------------------- | -------------------------------------------------- | ------------------------------------------------------------------------------------- |
| `java` o `javac` no se reconoce                    | JDK no instalado o fuera de `PATH`                 | Instalar JDK 17 y verificar `JAVA_HOME` y `%JAVA_HOME%\bin`                           |
| `mvn` no se reconoce                               | Maven no instalado/configurado                     | Añadir `apache-maven\bin` al `Path` o usar NetBeans                                   |
| `npm` no se reconoce                               | Node.js no instalado                               | Instalar Node 24 LTS y reabrir terminal                                               |
| `npm ci` falla                                     | Internet, permisos o lockfile incoherente          | Comprobar conexión y que exista `package-lock.json`                                   |
| `Port 8080 was already in use`                     | Otra aplicación ocupa el puerto                    | Cerrar el otro proceso; no cambiar el puerto sin actualizar `api.js`                  |
| Vite indica que `5173` está ocupado                | Otra instancia frontend usa ese puerto             | Cerrar esa instancia; conservar `5173` por CORS                                       |
| `Failed to configure a DataSource`                 | Falta configuración de BD                          | Crear `application.properties`, configurar `DB_URL`, `DB_USER`, `DB_PASSWORD`         |
| `Connection refused` a `5432`                      | PostgreSQL detenido o puerto incorrecto            | Comprobar servicio y conexión en pgAdmin                                              |
| `password authentication failed for user postgres` | Contraseña de BD incorrecta                        | Revisar `DB_PASSWORD`; no confundir con contraseña del login web                      |
| `Schema-validation: missing table/column`          | Copia de BD incompatible o incompleta              | Restaurar el respaldo actualizado y probado con las entidades Java                    |
| `401 Unauthorized`                                 | No hay JWT o venció                                | Iniciar sesión nuevamente; sin token es el comportamiento correcto                    |
| `403 Forbidden`                                    | El rol no tiene permiso                            | Probar con cuenta Directivo o revisar asignaciones/permisos                           |
| `CORS error` en el navegador                       | Frontend en puerto diferente o origen no permitido | Verificar `http://localhost:5173` y `SeguridadConfig.java`                            |
| `No se pudo conectar con el servidor`              | Java detenido, dirección equivocada                | Revisar `localhost:8080` y `frontend/src/shared/services/api.js`                      |
| Login devuelve credenciales incorrectas            | Cuenta inexistente, inactiva o contraseña errónea  | Verificar datos de demostración en `usuarios` y `roles`                               |
| Lista de alumnos/materias vacía                    | Base sin datos o sin relaciones                    | Restaurar respaldo con datos; comprobar asociaciones entre cursos, módulos y materias |
| El proyecto inicia, pero hay errores al guardar    | Respaldo incompatible o datos/roles insuficientes  | Revisar consola Java, respuesta HTTP y registros de PostgreSQL                        |

**Dirección del backend en código:** `frontend/src/shared/services/api.js` contiene `http://localhost:8080/api`.  
**CORS en Java:** `SeguridadConfig.java` permite `http://localhost:5173`.  
**Conexión JDBC:** se define por `spring.datasource.*` en `application.properties`.

Para revisar errores del frontend: navegador → tecla **F12** → pestañas **Console** y **Network**. Para revisar errores del backend: observar la terminal donde se ejecuta Maven o el JAR.

---

## 12. Preparación de la entrega por parte del equipo 

Este apartado corresponde a los autores del software, no a la instalación del profesor.

### 12.1. Generar una base de demostración compatible

1. Trabajar sobre una **copia de la base actual que ya funciona con Spring Boot**.
2. Anonimizar cualquier dato personal: no distribuir DNI, domicilios, teléfonos, correos o trayectorias reales de estudiantes o trabajadores.
3. Crear o verificar una cuenta **Directivo de demostración**, activa, con la contraseña hasheada usando BCrypt (el mismo mecanismo que `AuthServicio`).
4. Agregar datos académicos ficticios suficientes para demostrar los tres casos de uso.
5. Probar login, consulta de alumno, consulta de materia y registro de calificaciones con esa base.
6. Realizar un respaldo completo desde la computadora de desarrollo, por ejemplo con PowerShell (adaptar el nombre de la base original):

```powershell
& "C:\Program Files\PostgreSQL\18\bin\pg_dump.exe" `
  -h localhost -p 5432 -U postgres -d Colegio `
  -Fc --no-owner --no-privileges `
  -f "C:\Entrega_ISII\base_datos\colegio_demo.backup"
```

7. Restaurar **ese respaldo** en una base nueva llamada `colegio_demo` y repetir las pruebas. Esta restauración de prueba es la que confirma que la entrega puede reproducirse desde cero.
8. Entregar la contraseña de la cuenta de prueba por un canal acordado con la cátedra. No incluir claves de la base de la institución ni secretos JWT usados en equipos reales.

### 12.2. Preparar código fuente y ejecutable

**Backend Java:** desde la carpeta con `pom.xml`, ejecutar:

```powershell
mvn clean package
```

Incluir el código y el archivo generado en `target/ColegioTomasBaras-1.0-SNAPSHOT.jar` (si la compilación ha tenido éxito).

**Frontend React:** desde la carpeta con `package.json`, ejecutar:

```powershell
npm ci --include=dev
npm run build
```

El directorio `dist/` resultante contiene los archivos construidos del frontend. No ejecutar `dist/index.html` directamente con doble clic: la aplicación necesita un servidor HTTP para que funcionen correctamente las rutas y las solicitudes a Java. Para la evaluación local se recomienda `npm run dev` tal como se indica arriba, o configurar un servidor estático con las rutas SPA correctamente resueltas y el origen permitido por CORS.

**No es necesario incluir** `node_modules/`, `target/` o carpetas de caché dentro del ZIP de código fuente si el profesor dispone de internet para instalar dependencias; sí conviene incluir **el JAR**, el `package-lock.json` y **el respaldo de base**.

### 12.3. Verificación final de portabilidad

Se recomienda probar la carpeta de entrega en **otra computadora o perfil de Windows**, no solamente en la computadora de desarrollo. Ejecutar estrictamente los pasos del README y confirmar que funciona con **una base restaurada**, no con la base local original del equipo.

---

## 13. Resumen de inicio rápido (si ya está todo instalado)

1. Iniciar PostgreSQL y verificar la base `colegio_demo` restaurada.
2. Abrir **PowerShell 1**, definir `DB_URL`, `DB_USER`, `DB_PASSWORD` y `APP_JWT_SECRET` (sección 5).
3. En esa terminal: `cd C:\Entrega_ISII\Escuela-java-main` y `mvn spring-boot:run`.
4. Abrir **PowerShell 2**: `cd C:\Entrega_ISII\Escuela-main\frontend` y `npm ci --include=dev` (solo primera vez).
5. En esa terminal: `npm run dev -- --host localhost --port 5173 --strictPort`.
6. Abrir <http://localhost:5173/>, ingresar con las credenciales de **demostración** y probar los casos de uso.

**Orden recomendado:** PostgreSQL → Spring Boot → React → iniciar sesión.

---

## 14. Soporte y límites de esta guía

Esta guía fue redactada a partir de los archivos fuente más recientes proporcionados para Ingeniería de Software II y de las rutas observadas en React. **No se ejecutó en la computadora del profesor**, ni se pudo probar una restauración real de PostgreSQL en este entorno. La puesta en marcha definitiva exige que el grupo aporte y valide la copia de base de datos y las credenciales de demostración especificadas.

Para reportar problemas a los desarrolladores, adjuntar (sin contraseñas ni datos personales): versión de Windows; resultados de `java -version`, `mvn -version`, `node -v` y `npm -v`; última parte del error en la terminal de Java; y mensaje de **Console/Network** del navegador.

---

**Proyecto Integrador — Ingeniería de Software II — Sistema de Gestión Académica.**
