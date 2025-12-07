# 💰 GUÍA: Cotizaciones - Igual que Solicitudes

## 🎯 SÍ, las Cotizaciones Funcionan Igual

### ✅ Ambas las creas TÚ (el técnico)

| Concepto | Solicitud | Cotización |
|----------|-----------|------------|
| **Qué es** | Orden de trabajo/reparación | Presupuesto/estimación de costo |
| **Cuándo se usa** | Cliente ya decidió reparar | Cliente pregunta cuánto cuesta |
| **Quién la crea** | 🔧 TÚ (técnico) | 🔧 TÚ (técnico) |
| **Datos del cliente** | Nombre, servicio, descripción | Nombre, correo, teléfono, consola |
| **Endpoint Backend** | `/api/solicitudes` | `/api/cotizaciones` |
| **Pantalla en App** | "Gestión Backend" | (Puede estar en otra pantalla) |

---

## 📋 DIFERENCIA CLAVE

### Solicitud = "Ya voy a reparar"
```
Cliente: "Repara mi PS5"
Tú: Creas SOLICITUD
Estado: PENDIENTE → EN_PROCESO → COMPLETADO
```

### Cotización = "¿Cuánto cuesta?"
```
Cliente: "¿Cuánto me cobras por limpiar mi PS4?"
Tú: Creas COTIZACIÓN (presupuesto)
Cliente decide: "Ok, acepto" o "No gracias"
```

---

## 🔄 FLUJO COMPLETO (con ambos)

### Escenario Real:

```
DÍA 1:
👤 Cliente llama: "Hola, ¿cuánto cuesta reparar una PS5 que no enciende?"

🔧 TÚ:
   1. Creas COTIZACIÓN:
      - Cliente: "María López"
      - Tipo Consola: "PlayStation 5"
      - Problema: "No enciende"
      - Estado: "PENDIENTE"
   
   2. Le dices: "Te cuesta $500, pero necesito ver la consola"

👤 Cliente: "Ok, mañana la llevo"

────────────────────────────────────────

DÍA 2:
👤 Cliente trae la consola

🔧 TÚ:
   1. Revisas la consola
   2. Confirmas el costo
   3. Creas SOLICITUD (orden de trabajo):
      - Servicio: "Reparación PS5"
      - Cliente: "María López"
      - Estado: "PENDIENTE"
   
   4. Empiezas a repararla
   5. Cambias estado a "EN_PROCESO"

────────────────────────────────────────

DÍA 3:
🔧 TÚ:
   1. Terminas la reparación
   2. Cambias estado de SOLICITUD a "COMPLETADO"

👤 Cliente recoge su consola
```

---

## 🧪 CÓMO PROBAR COTIZACIONES EN SWAGGER

### PASO 1: Abre Swagger
```
http://localhost:8080/swagger-ui/index.html
```

### PASO 2: Busca el Controlador de Cotizaciones
- Puede llamarse: `cotizacion-controller` o similar

### PASO 3: Prueba POST /api/cotizaciones

**Click en "Try it out"** y usa este JSON:

```json
{
  "nombreCliente": "María López",
  "correoCliente": "maria@email.com",
  "telefonoCliente": "555-1234",
  "tipoConsola": "PlayStation 5",
  "modeloConsola": "PS5 Digital Edition",
  "descripcionProblema": "La consola no enciende, luz azul parpadeante",
  "estadoSolicitud": "PENDIENTE",
  "fechaSolicitud": "2024-12-06"
}
```

**Click "Execute"**

### PASO 4: Verifica el Resultado

✅ **Éxito:**
```
Code: 201 Created

Response body:
{
  "id": 1,
  "nombreCliente": "María López",
  "correoCliente": "maria@email.com",
  "tipoConsola": "PlayStation 5",
  "estadoSolicitud": "PENDIENTE",
  ...
}
```

### PASO 5: Ver Todas las Cotizaciones

**Prueba GET /api/cotizaciones**

Deberías ver la cotización que creaste.

---

## 📱 ¿Hay Pantalla para Cotizaciones en la App?

Déjame verificar si tu app tiene una pantalla para gestionar cotizaciones...

**Si NO hay pantalla todavía:**
- Por ahora puedes gestionar cotizaciones desde Swagger
- La funcionalidad está lista en el backend
- Solo faltaría crear la pantalla en la app (opcional)

**Si SÍ hay pantalla:**
- Funcionaría igual que "Gestión Backend" pero para cotizaciones

---

## 🎯 RESUMEN: Solicitudes vs Cotizaciones

### Ambas:
- ✅ Las creas TÚ (el técnico)
- ✅ Registran datos de clientes
- ✅ Se guardan en el backend
- ✅ Tienen estados (PENDIENTE, etc.)
- ✅ Pueden ser editadas/eliminadas

### Diferencia:
- **📋 Solicitud:** "Voy a reparar esta consola" (trabajo confirmado)
- **💰 Cotización:** "Esto costaría..." (presupuesto/estimación)

---

## 🧪 PRUEBA AHORA

### 1. Crea una Cotización en Swagger:

```json
{
  "nombreCliente": "Carlos Gómez",
  "correoCliente": "carlos@email.com",
  "telefonoCliente": "555-5678",
  "tipoConsola": "PlayStation 4",
  "modeloConsola": "PS4 Slim",
  "descripcionProblema": "Limpieza profunda, ventilador ruidoso",
  "estadoSolicitud": "PENDIENTE",
  "fechaSolicitud": "2024-12-06"
}
```

### 2. Verifica con GET que se creó

### 3. Luego crea una Solicitud relacionada:

```json
{
  "servicio": "Limpieza PlayStation 4",
  "cliente": "Carlos Gómez",
  "descripcion": "Limpieza profunda, cotización aceptada",
  "fechaSolicitud": "2024-12-06",
  "horaSolicitud": "15:00",
  "estadoSolicitud": "PENDIENTE",
  "idCategoria": 2
}
```

Ahora tienes:
- ✅ Una cotización (presupuesto)
- ✅ Una solicitud (trabajo a realizar)
- ✅ Ambas para el mismo cliente

---

## 💡 EN LA VIDA REAL

**No necesitas SIEMPRE crear ambas.**

### Opción 1: Solo Cotización
```
Cliente solo pregunta precio → Creas cotización → No acepta → Fin
```

### Opción 2: Solo Solicitud
```
Cliente ya sabe precio → Trae consola → Creas solicitud directo
```

### Opción 3: Ambas
```
Cotización primero → Cliente acepta → Creas solicitud
```

---

## 🎉 RESUMEN FINAL

**SÍ, las cotizaciones funcionan EXACTAMENTE IGUAL que las solicitudes:**

1. ✅ Tú las creas (como técnico)
2. ✅ Se guardan en el backend
3. ✅ Puedes verlas/editarlas/eliminarlas
4. ✅ Tienen el mismo flujo de estados
5. ✅ El backend ya está listo para usarlas

**Solo que sirven para propósitos diferentes:**
- 📋 **Solicitud:** Orden de trabajo confirmada
- 💰 **Cotización:** Presupuesto/estimación

---

¿Quieres que verifique si tu app tiene una pantalla para gestionar cotizaciones? 🚀

