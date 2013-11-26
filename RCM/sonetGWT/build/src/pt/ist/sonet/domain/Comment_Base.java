package pt.ist.sonet.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Comment_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.AP> role$$accessPoint = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.AP>() {
        public pt.ist.sonet.domain.AP getValue(pt.ist.sonet.domain.Comment o1) {
            return ((Comment_Base.DO_State)o1.get$obj$state(false)).accessPoint;
        }
        public void setValue(pt.ist.sonet.domain.Comment o1, pt.ist.sonet.domain.AP o2) {
            ((Comment_Base.DO_State)o1.get$obj$state(true)).accessPoint = o2;
        }
        public dml.runtime.Role<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Comment> getInverseRole() {
            return pt.ist.sonet.domain.AP.role$$comments;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.Agent> role$$agent = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.Agent>() {
        public pt.ist.sonet.domain.Agent getValue(pt.ist.sonet.domain.Comment o1) {
            return ((Comment_Base.DO_State)o1.get$obj$state(false)).agent;
        }
        public void setValue(pt.ist.sonet.domain.Comment o1, pt.ist.sonet.domain.Agent o2) {
            ((Comment_Base.DO_State)o1.get$obj$state(true)).agent = o2;
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Comment> getInverseRole() {
            return pt.ist.sonet.domain.Agent.role$$comments;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.AP> APHasComments = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.AP>(role$$accessPoint);
    static {
        pt.ist.sonet.domain.AP.APHasComments = APHasComments.getInverseRelation();
    }
    
    static {
        APHasComments.setRelationName("pt.ist.sonet.domain.Comment.APHasComments");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.Agent> AgentHasComments = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.Agent>(role$$agent);
    static {
        pt.ist.sonet.domain.Agent.AgentHasComments = AgentHasComments.getInverseRelation();
    }
    
    static {
        AgentHasComments.setRelationName("pt.ist.sonet.domain.Comment.AgentHasComments");
    }
    
    
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Comment_Base() {
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
    
    public java.lang.String getText() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "text");
        return ((DO_State)this.get$obj$state(false)).text;
    }
    
    public void setText(java.lang.String text) {
        ((DO_State)this.get$obj$state(true)).text = text;
    }
    
    private java.lang.String get$text() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).text;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$text(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).text = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public pt.ist.sonet.domain.AP getAccessPoint() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "accessPoint");
        return ((DO_State)this.get$obj$state(false)).accessPoint;
    }
    
    public void setAccessPoint(pt.ist.sonet.domain.AP accessPoint) {
        APHasComments.add((pt.ist.sonet.domain.Comment)this, accessPoint);
    }
    
    public boolean hasAccessPoint() {
        return (getAccessPoint() != null);
    }
    
    public void removeAccessPoint() {
        setAccessPoint(null);
    }
    
    private java.lang.Long get$oidAccessPoint() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).accessPoint;
        return (value == null) ? null : value.getOid();
    }
    
    @jvstm.cps.ConsistencyPredicate
    public final boolean checkMultiplicityOfAccessPoint() {
        if (! hasAccessPoint()) return false;
        return true;
    }
    
    public pt.ist.sonet.domain.Agent getAgent() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "agent");
        return ((DO_State)this.get$obj$state(false)).agent;
    }
    
    public void setAgent(pt.ist.sonet.domain.Agent agent) {
        AgentHasComments.add((pt.ist.sonet.domain.Comment)this, agent);
    }
    
    public boolean hasAgent() {
        return (getAgent() != null);
    }
    
    public void removeAgent() {
        setAgent(null);
    }
    
    private java.lang.Long get$oidAgent() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).agent;
        return (value == null) ? null : value.getOid();
    }
    
    @jvstm.cps.ConsistencyPredicate
    public final boolean checkMultiplicityOfAgent() {
        if (! hasAgent()) return false;
        return true;
    }
    
    protected void checkDisconnected() {
        if (hasAccessPoint()) handleAttemptToDeleteConnectedObject();
        if (hasAgent()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$id(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "ID"), state);
        set$text(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "TEXT"), state);
        castedState.accessPoint = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_ACCESS_POINT");
        castedState.agent = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_AGENT");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private int id;
        private java.lang.String text;
        private pt.ist.sonet.domain.AP accessPoint;
        private pt.ist.sonet.domain.Agent agent;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.id = this.id;
            newCasted.text = this.text;
            newCasted.accessPoint = this.accessPoint;
            newCasted.agent = this.agent;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private int id;
            private java.lang.String text;
            private pt.ist.sonet.domain.AP accessPoint;
            private pt.ist.sonet.domain.Agent agent;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.id = obj.id;
                this.text = obj.text;
                this.accessPoint = obj.accessPoint;
                this.agent = obj.agent;
                
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
                state.text = this.text;
                state.accessPoint = this.accessPoint;
                state.agent = this.agent;
                
            }
            
        }
        
    }
    
}
