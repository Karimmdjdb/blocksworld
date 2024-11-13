package blocksworld.planning.providers;

import java.util.Set;
import blocksworld.planning.actions.Action;

public interface ActionsProvider {
    public Set<Action> getActions();
}
