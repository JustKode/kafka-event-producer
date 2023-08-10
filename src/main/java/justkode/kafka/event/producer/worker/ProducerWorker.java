package justkode.kafka.event.producer.worker;

import justkode.kafka.event.producer.meta.Meta;

import java.util.Map;

public class ProducerWorker implements Runnable {
    private Map<String, Meta> metaMap;

    public ProducerWorker(Map<String, Meta> metaMap) {
        this.metaMap = metaMap;
    }

    @Override
    public void run() {

    }

    public void shutdown() {

    }
}
