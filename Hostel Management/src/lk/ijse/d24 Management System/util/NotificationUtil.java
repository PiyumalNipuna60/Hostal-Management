package util;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class NotificationUtil {

    public static void playNotification(AnimationType animationType, String title, NotificationType notificationType, Duration duration){
        TrayNotification notify = new TrayNotification();
        notify.setAnimationType(animationType);
        notify.setTitle(title);
        notify.setNotificationType(notificationType);
        notify.showAndDismiss(duration);
    }
}
