package api.vmware.employee.inventory.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * The enum File action.
 */
@Getter
@ToString
public enum FileAction {

    /**
     * Upload file action.
     */
    UPLOAD("upload");

    private final String action;

    FileAction(final String action) {
        this.action = action;
    }
}
