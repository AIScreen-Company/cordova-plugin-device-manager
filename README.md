# cordova-plugin-device-manager


### Install Cordova plugins 
```
$ cordova plugin add https://github.com/AIScreen-Company/cordova-plugin-device-manager.git

```
###  Usage

### 1. addAdmin(successCallback, errorCallback)

Добавляет администратора на устройство.



```
deviceManager.addAdmin(
    function() {
        console.log('Администратор успешно добавлен.');
    },
    function(error) {
        console.error('Ошибка при добавлении администратора: ' + error);
    }
); 
```
### 2. lockScreen(successCallback, errorCallback)

Блокирует экран устройства.

```
deviceManager.lockScreen(
    function() {
        console.log('Экран успешно заблокирован.');
    },
    function(error) {
        console.error('Ошибка при блокировке экрана: ' + error);
    }
);
```

### 3. removeAdmin(successCallback, errorCallback)

Удаляет администратора с устройства.

```
deviceManager.removeAdmin(
    function() {
        console.log('Администратор успешно удален.');
    },
    function(error) {
        console.error('Ошибка при удалении администратора: ' + error);
    }
);
``` 

### 4. isAdmin(successCallback, errorCallback)

Проверяет, является ли текущий пользователь администратором.

```
deviceManager.isAdmin(
    function(isAdmin) {
        console.log('Текущий пользователь ' + (isAdmin ? 'является' : 'не является') + ' администратором.');
    },
    function(error) {
        console.error('Ошибка при проверке статуса администратора: ' + error);
    }
);
``` 

### 5. onScreen(successCallback, errorCallback)

Включает экран устройства.

```
deviceManager.onScreen(
    function() {
        console.log('Экран успешно включен.');
    },
    function(error) {
        console.error('Ошибка при включении экрана: ' + error);
    }
);
``` 

### 6. offScreen(successCallback, errorCallback)

Выключает экран устройства.

```
deviceManager.offScreen(
    function() {
        console.log('Экран успешно выключен.');
    },
    function(error) {
        console.error('Ошибка при выключении экрана: ' + error);
    }
);
``` 

### 7. rootReboot(successCallback, errorCallback)

Перезагружает устройство с доступом root.

```
deviceManager.rootReboot(
    function() {
        console.log('Устройство успешно перезагружено с доступом root.');
    },
    function(error) {
        console.error('Ошибка при перезагрузке устройства с доступом root: ' + error);
    }
);
``` 

### 8. rootPowerOn(successCallback, errorCallback)

Включает/выключает устройство с доступом root.

```
deviceManager.rootPowerOn(
    function() {
        console.log('Устройство успешно включено/выключено с доступом root.');
    },
    function(error) {
        console.error('Ошибка при включении/выключении устройства с доступом root: ' + error);
    }
);
``` 

### 9. rootShutdown(successCallback, errorCallback)

Выключает устройство с доступом root.
```
deviceManager.rootShutdown(
    function() {
        console.log('Устройство успешно выключено с доступом root.');
    },
    function(error) {
        console.error('Ошибка при выключении устройства с доступом root: ' + error);
    }
);
``` 

## Замечания

-   Некоторые функции могут требовать привилегии администратора или root-доступа.
-   Проверьте совместимость функций с вашей целевой платформой и устройством.