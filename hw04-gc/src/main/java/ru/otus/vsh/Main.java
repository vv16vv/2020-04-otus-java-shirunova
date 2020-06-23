package ru.otus.vsh;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static final HashMap<String, AtomicLong> counters = new HashMap<>();

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        switchOnMonitoring();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus.vsh:type=Leaky");
        System.out.println("----------------");

        int quantAdd = 1000;
        int quantRemove = 500;

        var leaky = new Leaky(quantAdd, quantRemove);
        mbs.registerMBean(leaky, name);

        var start = System.currentTimeMillis();
        var finish = System.currentTimeMillis();
        var timeout = 10 * 60 * 1000; // 10 min

        while ((finish - start) <= timeout) {
            try {
                leaky.add();
                leaky.remove();
                finish = System.currentTimeMillis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcCause = info.getGcCause();

                    long duration = info.getGcInfo().getDuration();
                    updateAndPrintCounters(gcName, duration);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static void updateAndPrintCounters(String key, long duration) {
        counters.putIfAbsent(key, new AtomicLong(0));
        counters.get(key).getAndAdd(duration);
        System.out.println(counters);
    }
}

