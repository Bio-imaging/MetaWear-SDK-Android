/*
 * Copyright 2014-2015 MbientLab Inc. All rights reserved.
 *
 * IMPORTANT: Your use of this Software is limited to those specific rights granted under the terms of a software
 * license agreement between the user who downloaded the software, his/her employer (which must be your
 * employer) and MbientLab Inc, (the "License").  You may not use this Software unless you agree to abide by the
 * terms of the License which can be found at www.mbientlab.com/terms.  The License limits your use, and you
 * acknowledge, that the Software may be modified, copied, and distributed when used in conjunction with an
 * MbientLab Inc, product.  Other than for the foregoing purpose, you may not use, reproduce, copy, prepare
 * derivative works of, modify, distribute, perform, display or sell this Software and/or its documentation for any
 * purpose.
 *
 * YOU FURTHER ACKNOWLEDGE AND AGREE THAT THE SOFTWARE AND DOCUMENTATION ARE PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, TITLE,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT SHALL MBIENTLAB OR ITS LICENSORS BE LIABLE OR
 * OBLIGATED UNDER CONTRACT, NEGLIGENCE, STRICT LIABILITY, CONTRIBUTION, BREACH OF WARRANTY, OR OTHER LEGAL EQUITABLE
 * THEORY ANY DIRECT OR INDIRECT DAMAGES OR EXPENSES INCLUDING BUT NOT LIMITED TO ANY INCIDENTAL, SPECIAL, INDIRECT,
 * PUNITIVE OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA, COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY,
 * SERVICES, OR ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT LIMITED TO ANY DEFENSE THEREOF), OR OTHER SIMILAR COSTS.
 *
 * Should you have any questions regarding your right to use this Software, contact MbientLab via email:
 * hello@mbientlab.com.
 */

package com.mbientlab.metawear.impl;

import com.mbientlab.metawear.Data;

import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * Created by etsai on 9/4/16.
 */
class UintData extends DataTypeBase {
    private static final long serialVersionUID = -7137342633916883852L;

    UintData(ModuleId module, byte register, byte id, DataAttributes attributes) {
        super(module, register, id, attributes);
    }

    UintData(ModuleId module, byte register, DataAttributes attributes) {
        super(module, register, attributes);
    }

    UintData(DataTypeBase input, ModuleId module, byte register, byte id, DataAttributes attributes) {
        super(input, module, register, id, attributes);
    }

    UintData(DataTypeBase input, ModuleId module, byte register, DataAttributes attributes) {
        super(input, module, register, attributes);
    }

    @Override
    public DataTypeBase copy(DataTypeBase input, ModuleId module, byte register, byte id, DataAttributes attributes) {
        return new UintData(input, module, register, id, attributes);
    }

    @Override
    public Number convertToFirmwareUnits(MetaWearBoardPrivate owner, Number input) {
        return input;
    }

    @Override
    public Data createMessage(boolean logData, MetaWearBoardPrivate owner, final byte[] data, final Calendar timestamp) {
        final ByteBuffer buffer = Util.bytesToUIntBuffer(logData, data, attributes);

        return new DataPrivate(timestamp, data) {
            @Override
            public Class<?>[] types() {
                return new Class<?>[] {Long.class, Integer.class, Short.class, Byte.class, Boolean.class};
            }

            @Override
            public <T> T value(Class<T> clazz) {
                if (clazz == Boolean.class) {
                    return clazz.cast(buffer.get(0) != 0);
                }
                if (clazz == Long.class) {
                    return clazz.cast(buffer.getLong(0));
                }
                if (clazz == Integer.class) {
                    return clazz.cast(buffer.getInt(0));
                }
                if (clazz == Short.class) {
                    return clazz.cast(buffer.getShort(0));
                }
                if (clazz == Byte.class) {
                    return clazz.cast(buffer.get(0));
                }
                return super.value(clazz);
            }
        };
    }
}