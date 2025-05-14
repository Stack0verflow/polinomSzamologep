package ast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RuntimeContext {
    public Stack<Map<String, Polynomial>> scope;
    public Map<String, Polynomial> variables;

    public RuntimeContext() {
        this.scope = new Stack<>();
        this.variables = new HashMap<>();
    }

    public void newFrame() {
        this.scope.push(this.variables);
        this.variables = new HashMap<>();
    }

    public void popFrame() {
        this.variables = this.scope.pop();
    }

    public Polynomial getVariable(String name) {
        Polynomial local = this.variables.get(name);

        if (local == null) {
            local = this.scope.firstElement().get(name);
        }

        return local;
    }
}
