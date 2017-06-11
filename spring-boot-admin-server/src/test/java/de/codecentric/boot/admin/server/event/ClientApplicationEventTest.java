/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.codecentric.boot.admin.server.event;

import de.codecentric.boot.admin.server.model.Application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientApplicationEventTest {

    @Test
    public void hashCode_equals() throws Exception {
        ClientApplicationEvent event1 = new ClientApplicationRegisteredEvent(
                Application.create("test").withHealthUrl("http://health").build());
        ClientApplicationEvent event2 = cloneBySerialization(event1);

        assertThat(event1.hashCode()).isEqualTo(event2.hashCode());
        assertThat(event1).isEqualTo(event2);
    }

    @Test
    public void equals() throws Exception {
        ClientApplicationEvent event1 = new ClientApplicationRegisteredEvent(
                Application.create("test").withHealthUrl("http://health").build());
        ClientApplicationEvent event2 = new ClientApplicationDeregisteredEvent(
                Application.create("test").withHealthUrl("http://health").build());

        assertThat(event1).isNotEqualTo(event2);
    }

    @SuppressWarnings("unchecked")
    /**
     * yeah nasty but we need exact the same timestamp
     */ private <T extends Serializable> T cloneBySerialization(T obj) throws IOException, ClassNotFoundException {
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(buf)) {
                oos.writeObject(obj);
            }

            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()))) {
                return (T) ois.readObject();
            }
        }
    }

}
