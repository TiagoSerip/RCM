package pt.ist.sonet.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class AP_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.SoNet> role$$sonet = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.SoNet>() {
        public pt.ist.sonet.domain.SoNet getValue(pt.ist.sonet.domain.AP o1) {
            return ((AP_Base.DO_State)o1.get$obj$state(false)).sonet;
        }
        public void setValue(pt.ist.sonet.domain.AP o1, pt.ist.sonet.domain.SoNet o2) {
            ((AP_Base.DO_State)o1.get$obj$state(true)).sonet = o2;
        }
        public dml.runtime.Role<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.AP> getInverseRole() {
            return pt.ist.sonet.domain.SoNet.role$$ap;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Agent> role$$agent = new dml.runtime.RoleMany<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Agent>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.Agent> getSet(pt.ist.sonet.domain.AP o1) {
            return ((AP_Base)o1).get$rl$agent();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.AP> getInverseRole() {
            return pt.ist.sonet.domain.Agent.role$$accessPoint;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Comment> role$$comments = new dml.runtime.RoleMany<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Comment>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.Comment> getSet(pt.ist.sonet.domain.AP o1) {
            return ((AP_Base)o1).get$rl$comments();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.AP> getInverseRole() {
            return pt.ist.sonet.domain.Comment.role$$ap;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.SoNet> SonetHasAPs = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.SoNet>(role$$sonet);
    static {
        pt.ist.sonet.domain.SoNet.SonetHasAPs = SonetHasAPs.getInverseRelation();
    }
    
    static {
        SonetHasAPs.setRelationName("pt.ist.sonet.domain.AP.SonetHasAPs");
    }
    public static dml.runtime.Relation<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Agent> APHasAgents;
    public static dml.runtime.Relation<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Comment> APHasComments;
    
    
    private RelationList<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Agent> get$rl$agent() {
        return get$$relationList("agent", APHasAgents);
        
    }
    private RelationList<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Comment> get$rl$comments() {
        return get$$relationList("comments", APHasComments);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  AP_Base() {
        super();
    }
    
    public int getId() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "id");
        return ((DO_State)this.get$obj$state(false)).id;
    }
    
    public void setId(int id) {
        ((DO_State)this.get$obj$state(true)).id = id;
    }
    
    private int get$id() {
        int value = ((DO_State)this.get$obj$state(false)).id;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$id(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).id = (int)(arg0);
    }
    
    public java.lang.String getSubnet() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "subnet");
        return ((DO_State)this.get$obj$state(false)).subnet;
    }
    
    public void setSubnet(java.lang.String subnet) {
        ((DO_State)this.get$obj$state(true)).subnet = subnet;
    }
    
    private java.lang.String get$subnet() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).subnet;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$subnet(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).subnet = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public int getPosVotes() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "posVotes");
        return ((DO_State)this.get$obj$state(false)).posVotes;
    }
    
    public void setPosVotes(int posVotes) {
        ((DO_State)this.get$obj$state(true)).posVotes = posVotes;
    }
    
    private int get$posVotes() {
        int value = ((DO_State)this.get$obj$state(false)).posVotes;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$posVotes(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).posVotes = (int)(arg0);
    }
    
    public int getNegVotes() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "negVotes");
        return ((DO_State)this.get$obj$state(false)).negVotes;
    }
    
    public void setNegVotes(int negVotes) {
        ((DO_State)this.get$obj$state(true)).negVotes = negVotes;
    }
    
    private int get$negVotes() {
        int value = ((DO_State)this.get$obj$state(false)).negVotes;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$negVotes(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).negVotes = (int)(arg0);
    }
    
    public pt.ist.sonet.domain.SoNet getSonet() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "sonet");
        return ((DO_State)this.get$obj$state(false)).sonet;
    }
    
    public void setSonet(pt.ist.sonet.domain.SoNet sonet) {
        SonetHasAPs.add((pt.ist.sonet.domain.AP)this, sonet);
    }
    
    public boolean hasSonet() {
        return (getSonet() != null);
    }
    
    public void removeSonet() {
        setSonet(null);
    }
    
    private java.lang.Long get$oidSonet() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).sonet;
        return (value == null) ? null : value.getOid();
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
        APHasAgents.add((pt.ist.sonet.domain.AP)this, agent);
    }
    
    public void removeAgent(pt.ist.sonet.domain.Agent agent) {
        APHasAgents.remove((pt.ist.sonet.domain.AP)this, agent);
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
    
    public int getCommentsCount() {
        return get$rl$comments().size();
    }
    
    public boolean hasAnyComments() {
        return (! get$rl$comments().isEmpty());
    }
    
    public boolean hasComments(pt.ist.sonet.domain.Comment comments) {
        return get$rl$comments().contains(comments);
    }
    
    public java.util.Set<pt.ist.sonet.domain.Comment> getCommentsSet() {
        return get$rl$comments();
    }
    
    public void addComments(pt.ist.sonet.domain.Comment comments) {
        APHasComments.add((pt.ist.sonet.domain.AP)this, comments);
    }
    
    public void removeComments(pt.ist.sonet.domain.Comment comments) {
        APHasComments.remove((pt.ist.sonet.domain.AP)this, comments);
    }
    
    public java.util.List<pt.ist.sonet.domain.Comment> getComments() {
        return get$rl$comments();
    }
    
    public void set$comments(OJBFunctionalSetWrapper comments) {
        get$rl$comments().setFromOJB(this, "comments", comments);
    }
    
    public java.util.Iterator<pt.ist.sonet.domain.Comment> getCommentsIterator() {
        return get$rl$comments().iterator();
    }
    
    protected void checkDisconnected() {
        if (hasSonet()) handleAttemptToDeleteConnectedObject();
        if (hasAnyAgent()) handleAttemptToDeleteConnectedObject();
        if (hasAnyComments()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$id(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "ID"), state);
        set$subnet(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "SUBNET"), state);
        set$posVotes(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "POS_VOTES"), state);
        set$negVotes(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "NEG_VOTES"), state);
        castedState.sonet = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_SONET");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("agent")) return APHasAgents;
        if (attrName.equals("comments")) return APHasComments;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("agent", APHasAgents);
        get$$relationList("comments", APHasComments);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int id;
        private java.lang.String subnet;
        private int posVotes;
        private int negVotes;
        private pt.ist.sonet.domain.SoNet sonet;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.id = this.id;
            newCasted.subnet = this.subnet;
            newCasted.posVotes = this.posVotes;
            newCasted.negVotes = this.negVotes;
            newCasted.sonet = this.sonet;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int id;
            private java.lang.String subnet;
            private int posVotes;
            private int negVotes;
            private pt.ist.sonet.domain.SoNet sonet;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.id = obj.id;
                this.subnet = obj.subnet;
                this.posVotes = obj.posVotes;
                this.negVotes = obj.negVotes;
                this.sonet = obj.sonet;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.id = this.id;
                state.subnet = this.subnet;
                state.posVotes = this.posVotes;
                state.negVotes = this.negVotes;
                state.sonet = this.sonet;
                
            }
            
        }
        
    }
    
}
