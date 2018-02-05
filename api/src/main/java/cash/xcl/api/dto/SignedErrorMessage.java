package cash.xcl.api.dto;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.BytesIn;

import static cash.xcl.api.dto.Validators.notNullOrEmpty;

/**
 * A generic application message has been reported.  These should be used as little as possible as they cannot be easily processed downstream.
 */
public class SignedErrorMessage extends SignedMessage {

    private long origSourceAddress;
    private long origSourceEventTime;
    private int origMessageType;
    private String reason;

    public SignedErrorMessage(long sourceAddress, long eventTime, int origMessageType, long origSourceAddress, long origSourceEventTime, String reason) {
        super(sourceAddress, eventTime);
        this.origMessageType = origMessageType;
        this.origSourceAddress = origSourceAddress;
        this.origSourceEventTime = origSourceEventTime;
        this.reason = reason;
    }

    public SignedErrorMessage() {

    }

    public SignedErrorMessage messageType(int messageType) {
        this.origMessageType = messageType;
        return this;
    }

    public long origSourceAddress() {
        return origSourceAddress;
    }

    public SignedErrorMessage origSourceAddress(long origSourceAddress) {
        this.origSourceAddress = origSourceAddress;
        return this;
    }

    public long origSourceEventTime() {
        return origSourceEventTime;
    }

    public SignedErrorMessage origSourceEventTime(long origSourceEventTime) {
        this.origSourceEventTime = origSourceEventTime;
        return this;
    }

    public String reason() {
        return reason;
    }

    public SignedErrorMessage reason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getReason() {
        return reason;
    }

    private void setReason(String reason) {
        this.reason = notNullOrEmpty(reason);
    }

    @Override
    protected void readMarshallable2(BytesIn bytes) {
        origMessageType = bytes.readUnsignedByte();
        origSourceAddress = bytes.readLong();
        origSourceEventTime = bytes.readLong();
        reason = bytes.readUtf8();
    }

    @Override
    public int messageType() {
        return MethodIds.COMMAND_FAILED_EVENT;
    }

    @Override
    protected void writeMarshallable2(Bytes bytes) {
        bytes.writeUnsignedByte(origMessageType);
        bytes.writeLong(origSourceAddress);
        bytes.writeLong(origSourceEventTime);
        bytes.writeUtf8(reason);
    }
}