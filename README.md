# Configuración de Notificaciones de Stock

Para configurar las notificaciones del stock con los correos de tu empresa, sigue estos pasos:

1. **Modificar `Ferreteria\src\main\java\servlets\facturaServ.java`**
   
   - En la línea 186, establece el correo que enviará las notificaciones:
     ```java
     mailProperties.put("mail.smtp.user", "correo@outlook.com");
     ```

   - En la línea 187, coloca la contraseña del correo mencionado arriba:
     ```java
     mailProperties.put("mail.smtp.password", "Contraseña");
     ```

   - Finalmente, en la línea 201, configura el correo general que recibirá las notificaciones:
     ```java
     m.enviarEmail(subject, message, "correo@gmail.com");
     ```

2. **Modificar `Ferreteria\src\main\java\servlets\agregarProductoServ.java`**
   
   - En la línea 133, establece el correo que enviará las notificaciones:
     ```java
     mailProperties.put("mail.smtp.user", "correo@outlook.com");
     ```

   - En la línea 134, coloca la contraseña del correo mencionado arriba:
     ```java
     mailProperties.put("mail.smtp.password", "Contraseña");
     ```

   - Finalmente, en la línea 142, configura el correo general que recibirá las notificaciones:
     ```java
     m.enviarEmail(subject, message, "correo@gmail.com");
     ```

3. **Modificar `Ferreteria\src\main\java\servlets\productosServ.java`**
   
   - En la línea 253, establece el correo que enviará las notificaciones:
     ```java
     mailProperties.put("mail.smtp.user", "correo@outlook.com");
     ```

   - En la línea 254, coloca la contraseña del correo mencionado arriba:
     ```java
     mailProperties.put("mail.smtp.password", "Contraseña");
     ```

   - Finalmente, en la línea 261, configura el correo general que recibirá las notificaciones:
     ```java
     m.enviarEmail(subject, message, "correo@gmail.com");
     ```

**NOTA:** El correo que envía las notificaciones debe ser de Outlook, mientras que el que las recibe puede ser de cualquier tipo.
