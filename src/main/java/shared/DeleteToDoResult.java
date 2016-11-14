package shared;

import net.customware.gwt.dispatch.shared.Result;

public class DeleteToDoResult implements Result {

    private boolean result;

    public DeleteToDoResult() {
    }

    public DeleteToDoResult(boolean result) {
        this.result = result;
    }

    public boolean getToDoDTO() {
        return result;
    }
}
