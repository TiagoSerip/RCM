package pt.ist.sonet.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class SoNet_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.Agent> role$$agent = new dml.runtime.RoleMany<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.Agent>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.Agent> getSet(pt.ist.sonet.domain.SoNet o1) {
            return ((SoNet_Base)o1).get$rl$agent();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.SoNet> getInverseRole() {
            return pt.ist.sonet.domain.Agent.role$$sonet;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.AP> role$$ap = new dml.runtime.RoleMany<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.AP>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.AP> getSet(pt.ist.sonet.domain.SoNet o1) {
            return ((SoNet_Base)o1).get$rl$ap();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.SoNet> getInverseRole() {
            return pt.ist.sonet.domain.AP.role$$sonet;
        }
        
    };
    public static dml.runtime.Relation<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.Agent> SonetHasAgents;
    public static dml.runtime.Relation<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.AP> SonetHasAPs;
    
    
    private RelationList<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.Agent> get$rl$agent() {
        return get$$relationList("agent", SonetHasAgents);
        
    }
    private RelationList<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.AP> get$rl$ap() {
        return get$$relationList("ap", SonetHasAPs);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  SoNet_Base() {
        super();
    }
    
    public int getPIId() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "PIId");
        return ((DO_State)this.get$obj$state(false)).PIId;
    }
    
    public void setPIId(int PIId) {
        ((DO_State)this.get$obj$state(true)).PIId = PIId;
    }
    
    private int get$PIId() {
        int value = ((DO_State)this.get$obj$state(false)).PIId;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$PIId(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).PIId = (int)(arg0);
    }
    
    public int getAgentCount() {
        return get$rl$agent().size();
    }
    
    public boolean hasAnyAgent() {
        return (! get$rl$agent().isEmpty());
    }
    
    public boolean hasAgent(pt.ist.sonet.domain.Agent agent) {
        return get$rl$agent().contains(agent);
    }
    
    public java.util.Set<pt.ist.sonet.domain.Agent> getAgentSet() {
        return get$rl$agent();
    }
    
    public void addAgent(pt.ist.sonet.domain.Agent agent) {
        SonetHasAgents.add((pt.ist.sonet.domain.SoNet)this, agent);
    }
    
    public void removeAgent(pt.ist.sonet.domain.Agent agent) {
        SonetHasAgents.remove((pt.ist.sonet.domain.SoNet)this, agent);
    }
    
    public java.util.List<pt.ist.sonet.domain.Agent> getAgent() {
        return get$rl$agent();
    }
    
    public void set$agent(OJBFunctionalSetWrapper agent) {
        get$rl$agent().setFromOJB(this, "agent", agent);
    }
    
    public java.util.Iterator<pt.ist.sonet.domain.Agent> getAgentIterator() {
        return get$rl$agent().iterator();
    }
    
    public int getApCount() {
        return get$rl$ap().size();
    }
    
    public boolean hasAnyAp() {
        return (! get$rl$ap().isEmpty());
    }
    
    public boolean hasAp(pt.ist.sonet.domain.AP ap) {
        return get$rl$ap().contains(ap);
    }
    
    public java.util.Set<pt.ist.sonet.domain.AP> getApSet() {
        return get$rl$ap();
    }
    
    public void addAp(pt.ist.sonet.domain.AP ap) {
        SonetHasAPs.add((pt.ist.sonet.domain.SoNet)this, ap);
    }
    
    public void removeAp(pt.ist.sonet.domain.AP ap) {
        SonetHasAPs.remove((pt.ist.sonet.domain.SoNet)this, ap);
    }
    
    public java.util.List<pt.ist.sonet.domain.AP> getAp() {
        return get$rl$ap();
    }
    
    public void set$ap(OJBFunctionalSetWrapper ap) {
        get$rl$ap().setFromOJB(this, "ap", ap);
    }
    
    public java.util.Iterator<pt.ist.sonet.domain.AP> getApIterator() {
        return get$rl$ap().iterator();
    }
    
    protected void checkDisconnected() {
        if (hasAnyAgent()) handleAttemptToDeleteConnectedObject();
        if (hasAnyAp()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$PIId(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "P_I_ID"), state);
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("agent")) return SonetHasAgents;
        if (attrName.equals("ap")) return SonetHasAPs;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("agent", SonetHasAgents);
        get$$relationList("ap", SonetHasAPs);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int PIId;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.PIId = this.PIId;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int PIId;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.PIId = obj.PIId;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.PIId = this.PIId;
                
            }
            
        }
        
    }
    
}
