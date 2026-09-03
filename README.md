# Dulce Esmeralda

Sitio web y administración para la cafetería **Dulce Esmeralda**.

> Estado: **borrador inicial** (draft). Estructura basada en el proyecto plantilla *Librantis*.

## Tecnología

- Java 21 · Spring Boot 4
- Spring MVC + Thymeleaf
- Spring Data JPA
- Spring Security (login por base de datos, contraseñas BCrypt)
- Bootstrap 5 + Font Awesome
- Base de datos: **H2 en memoria** para desarrollo/demo · **MySQL** para producción

## Cómo ejecutar (demo, sin instalar base de datos)

```bash
mvn spring-boot:run
```

Arranca con el perfil `dev` (H2 en memoria) y datos de ejemplo (menú + usuario admin).

- App: http://localhost:8080
- Consola H2: http://localhost:8080/h2-console — JDBC URL `jdbc:h2:mem:dulceesmeralda`, usuario `sa`, sin contraseña
- Usuario administrador: `admin` / `cambiar123` (configurable con `APP_ADMIN_USERNAME` / `APP_ADMIN_PASSWORD`)

## Ejecutar con MySQL (producción)

1. Crear el esquema con [`src/main/resources/db/schema.sql`](src/main/resources/db/schema.sql).
2. Definir variables de entorno:

```bash
set SPRING_PROFILES_ACTIVE=prod
set DB_URL=jdbc:mysql://localhost:3306/dulceesmeralda
set DB_USERNAME=usuario
set DB_PASSWORD=secreto
set APP_ADMIN_PASSWORD=una-clave-fuerte
mvn spring-boot:run
```

Ninguna credencial se guarda en el repositorio. Para configuración local usar
`src/main/resources/application-local.properties` (ignorado por Git).

## Despliegue en Render (demo)

El repo incluye [`Dockerfile`](Dockerfile) y [`render.yaml`](render.yaml).

1. En [render.com](https://render.com) → **New** → **Blueprint** y conectá el repo `jodagova/DulceEsmeralda`.
2. Render lee `render.yaml` y crea un Web Service (plan free, Docker).
3. Al terminar el build queda en `https://dulce-esmeralda.onrender.com`.

La demo corre con perfil `dev` (**H2 en memoria**): los datos se reinician en cada
redeploy y cuando el plan free suspende el servicio por inactividad (~15 min).
La contraseña del admin la genera Render — se ve en **Environment → APP_ADMIN_PASSWORD**.

Para datos persistentes: crear un Postgres/MySQL en Render, agregar el driver y
pasar a perfil `prod` con las variables `DB_URL` / `DB_USERNAME` / `DB_PASSWORD`.

## Estructura

```
src/main/java/com/dulceesmeralda
├── config      · seguridad, MVC/i18n, carga de datos inicial
├── controller  · Index, Menu, Admin, Home
├── domain      · Usuario, Rol, Categoria, Producto, Pedido, PedidoDetalle
├── repository  · repositorios Spring Data
└── service     · lógica de negocio
```

## Pendiente (roadmap del draft)

- [ ] CRUD de productos y categorías desde el panel admin
- [ ] Registro de clientes y recuperación de contraseña
- [ ] Carrito y flujo de pedido
- [ ] Gestión de estados de pedido para el personal
- [ ] Carga de imágenes de productos
- [ ] Diseño final acordado con el cliente
