/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal;

import org.apache.ignite.internal.util.typedef.internal.*;
import org.apache.ignite.lang.*;
import org.apache.ignite.plugin.extensions.communication.*;

import java.io.*;
import java.nio.*;

/**
 * Request for cancelling tasks.
 */
public class GridTaskCancelRequest implements Message {
    /** */
    private static final long serialVersionUID = 0L;

    /** */
    private IgniteUuid sesId;

    /**
     * No-op constructor to support {@link Externalizable} interface.
     * This constructor is not meant to be used for other purposes.
     */
    public GridTaskCancelRequest() {
        // No-op.
    }

    /**
     * @param sesId Task session ID.
     */
    public GridTaskCancelRequest(IgniteUuid sesId) {
        assert sesId != null;

        this.sesId = sesId;
    }

    /**
     * Gets execution ID of task to be cancelled.
     *
     * @return Execution ID of task to be cancelled.
     */
    public IgniteUuid sessionId() {
        return sesId;
    }

    /** {@inheritDoc} */
    @Override public boolean writeTo(ByteBuffer buf, MessageWriter writer) {
        writer.setBuffer(buf);

        if (!writer.isHeaderWritten()) {
            if (!writer.writeHeader(directType(), fieldsCount()))
                return false;

            writer.onHeaderWritten();
        }

        switch (writer.state()) {
            case 0:
                if (!writer.writeIgniteUuid("sesId", sesId))
                    return false;

                writer.incrementState();

        }

        return true;
    }

    /** {@inheritDoc} */
    @Override public boolean readFrom(ByteBuffer buf, MessageReader reader) {
        reader.setBuffer(buf);

        if (!reader.beforeMessageRead())
            return false;

        switch (reader.state()) {
            case 0:
                sesId = reader.readIgniteUuid("sesId");

                if (!reader.isLastRead())
                    return false;

                reader.incrementState();

        }

        return true;
    }

    /** {@inheritDoc} */
    @Override public byte directType() {
        return 5;
    }

    /** {@inheritDoc} */
    @Override public byte fieldsCount() {
        return 1;
    }

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(GridTaskCancelRequest.class, this);
    }
}
