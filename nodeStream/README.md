La aplicación está lista para correr como un daemon en Unix. Para lograrlo es necesario seguir los siguientes pasos:

0-Modificar al fichero nodeStream indicando el class path para java de donde se encuentra el proyecto y sus dependencias. Tomar como guía
  la forma que está actualmente el fichero.

1-Copiar el archivo nodeStream en el directorio /etc/init.d

2-Darle permiso de ejecución "sudo chmod +x /etc/init.d/nodeStream"

3-Iniciar el servicio: "sh /etc/init.d/nodeStream start" o "sudo service nodeStream start"

4-Detener el servicio: "sh /etc/init.d/nodeStream stop" o  "sudo service nodeStream stop"

5-Pasar comandos de configuración: "sh /etc/init.d/nodeStream node-config-file $configFile node-exec-file $execFile interval $interval" o "sudo service nodeStream node-config-file $configFile node-exec-file $execFile interval $interval"

6-Crear stream "sh /etc/init.d/nodeStream create-stream $streamId pull-from $pull" o "sudo service nodeStream create-stream $streamId pull-from $pull"

7-Eliminar stream "sh /etc/init.d/nodeStream delete-stream $streamId" o "sudo service nodeStream delete-stream $streamId"

8-En Ubuntu si se desea que el servicio se inicie al iniciar la pc. "sudo update-rc.d nodeStream defaults" o "sudo update-rc.d nodeStream disable" para que no se inicie automáticamente
