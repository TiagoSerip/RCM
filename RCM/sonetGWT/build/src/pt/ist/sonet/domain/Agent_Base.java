package pt.ist.sonet.domain;

import pt.ist.fenixframework.pstm.VBox;
import pt.ist.fenixframework.pstm.RelationList;
import pt.ist.fenixframework.pstm.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializationGenerator.*;
public abstract class Agent_Base extends pt.ist.fenixframework.pstm.OneBoxDomainObject {
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.SoNet> role$$sonet = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.SoNet>() {
        public pt.ist.sonet.domain.SoNet getValue(pt.ist.sonet.domain.Agent o1) {
            return ((Agent_Base.DO_State)o1.get$obj$state(false)).sonet;
        }
        public void setValue(pt.ist.sonet.domain.Agent o1, pt.ist.sonet.domain.SoNet o2) {
            ((Agent_Base.DO_State)o1.get$obj$state(true)).sonet = o2;
        }
        public dml.runtime.Role<pt.ist.sonet.domain.SoNet,pt.ist.sonet.domain.Agent> getInverseRole() {
            return pt.ist.sonet.domain.SoNet.role$$agent;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message> role$$receivedMessage = new dml.runtime.RoleMany<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.Message> getSet(pt.ist.sonet.domain.Agent o1) {
            return ((Agent_Base)o1).get$rl$receivedMessage();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Message,pt.ist.sonet.domain.Agent> getInverseRole() {
            return pt.ist.sonet.domain.Message.role$$receiver;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message> role$$sentMessage = new dml.runtime.RoleMany<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.Message> getSet(pt.ist.sonet.domain.Agent o1) {
            return ((Agent_Base)o1).get$rl$sentMessage();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Message,pt.ist.sonet.domain.Agent> getInverseRole() {
            return pt.ist.sonet.domain.Message.role$$sender;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.AP> role$$ap = new pt.ist.fenixframework.pstm.dml.RoleOne<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.AP>() {
        public pt.ist.sonet.domain.AP getValue(pt.ist.sonet.domain.Agent o1) {
            return ((Agent_Base.DO_State)o1.get$obj$state(false)).ap;
        }
        public void setValue(pt.ist.sonet.domain.Agent o1, pt.ist.sonet.domain.AP o2) {
            ((Agent_Base.DO_State)o1.get$obj$state(true)).ap = o2;
        }
        public dml.runtime.Role<pt.ist.sonet.domain.AP,pt.ist.sonet.domain.Agent> getInverseRole() {
            return pt.ist.sonet.domain.AP.role$$agent;
        }
        
    };
    public final static dml.runtime.RoleMany<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Comment> role$$comments = new dml.runtime.RoleMany<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Comment>() {
        public dml.runtime.RelationBaseSet<pt.ist.sonet.domain.Comment> getSet(pt.ist.sonet.domain.Agent o1) {
            return ((Agent_Base)o1).get$rl$comments();
        }
        public dml.runtime.Role<pt.ist.sonet.domain.Comment,pt.ist.sonet.domain.Agent> getInverseRole() {
            return pt.ist.sonet.domain.Comment.role$$agent;
        }
        
    };
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.SoNet> SonetHasAgents = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.SoNet>(role$$sonet);
    static {
        pt.ist.sonet.domain.SoNet.SonetHasAgents = SonetHasAgents.getInverseRelation();
    }
    
    static {
        SonetHasAgents.setRelationName("pt.ist.sonet.domain.Agent.SonetHasAgents");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message> MessagesHasReceiver = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message>(role$$receivedMessage);
    static {
        pt.ist.sonet.domain.Message.MessagesHasReceiver = MessagesHasReceiver.getInverseRelation();
    }
    
    static {
        MessagesHasReceiver.setRelationName("pt.ist.sonet.domain.Agent.MessagesHasReceiver");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message> MessageHasSender = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message>(role$$sentMessage);
    static {
        pt.ist.sonet.domain.Message.MessageHasSender = MessageHasSender.getInverseRelation();
    }
    
    static {
        MessageHasSender.setRelationName("pt.ist.sonet.domain.Agent.MessageHasSender");
    }
    public final static pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.AP> APHasAgents = new pt.ist.fenixframework.pstm.LoggingRelation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.AP>(role$$ap);
    static {
        pt.ist.sonet.domain.AP.APHasAgents = APHasAgents.getInverseRelation();
    }
    
    static {
        APHasAgents.setRelationName("pt.ist.sonet.domain.Agent.APHasAgents");
    }
    public static dml.runtime.Relation<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Comment> AgentHasComments;
    
    
    private RelationList<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message> get$rl$receivedMessage() {
        return get$$relationList("receivedMessage", MessagesHasReceiver);
        
    }
    private RelationList<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Message> get$rl$sentMessage() {
        return get$$relationList("sentMessage", MessageHasSender);
        
    }
    private RelationList<pt.ist.sonet.domain.Agent,pt.ist.sonet.domain.Comment> get$rl$comments() {
        return get$$relationList("comments", AgentHasComments);
        
    }
    
    
    private void initInstance() {
        initInstance(true);
    }
    
    private void initInstance(boolean allocateOnly) {
        
    }
    
    {
        initInstance(false);
    }
    
    protected  Agent_Base() {
        super();
    }
    
    public java.lang.String getUsername() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "username");
        return ((DO_State)this.get$obj$state(false)).username;
    }
    
    public void setUsername(java.lang.String username) {
        ((DO_State)this.get$obj$state(true)).username = username;
    }
    
    private java.lang.String get$username() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).username;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$username(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).username = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public java.lang.String getName() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "name");
        return ((DO_State)this.get$obj$state(false)).name;
    }
    
    public void setName(java.lang.String name) {
        ((DO_State)this.get$obj$state(true)).name = name;
    }
    
    private java.lang.String get$name() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).name;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$name(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).name = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public java.lang.String getPassword() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "password");
        return ((DO_State)this.get$obj$state(false)).password;
    }
    
    public void setPassword(java.lang.String password) {
        ((DO_State)this.get$obj$state(true)).password = password;
    }
    
    private java.lang.String get$password() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).password;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$password(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).password = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public int getRssi() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "rssi");
        return ((DO_State)this.get$obj$state(false)).rssi;
    }
    
    public void setRssi(int rssi) {
        ((DO_State)this.get$obj$state(true)).rssi = rssi;
    }
    
    private int get$rssi() {
        int value = ((DO_State)this.get$obj$state(false)).rssi;
        return pt.ist.fenixframework.pstm.ToSqlConverter.getValueForint(value);
    }
    
    private final void set$rssi(int arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).rssi = (int)(arg0);
    }
    
    public java.lang.String getIp() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "ip");
        return ((DO_State)this.get$obj$state(false)).ip;
    }
    
    public void setIp(java.lang.String ip) {
        ((DO_State)this.get$obj$state(true)).ip = ip;
    }
    
    private java.lang.String get$ip() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).ip;
        return (value == null) ? null : pt.ist.fenixframework.pstm.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$ip(java.lang.String arg0, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).ip = (java.lang.String)((arg0 == null) ? null : arg0);
    }
    
    public pt.ist.sonet.domain.SoNet getSonet() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "sonet");
        return ((DO_State)this.get$obj$state(false)).sonet;
    }
    
    public void setSonet(pt.ist.sonet.domain.SoNet sonet) {
        SonetHasAgents.add((pt.ist.sonet.domain.Agent)this, sonet);
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
    
    @jvstm.cps.ConsistencyPredicate
    public final boolean checkMultiplicityOfSonet() {
        if (! hasSonet()) return false;
        return true;
    }
    
    public int getReceivedMessageCount() {
        return get$rl$receivedMessage().size();
    }
    
    public boolean hasAnyReceivedMessage() {
        return (! get$rl$receivedMessage().isEmpty());
    }
    
    public boolean hasReceivedMessage(pt.ist.sonet.domain.Message receivedMessage) {
        return get$rl$receivedMessage().contains(receivedMessage);
    }
    
    public java.util.Set<pt.ist.sonet.domain.Message> getReceivedMessageSet() {
        return get$rl$receivedMessage();
    }
    
    public void addReceivedMessage(pt.ist.sonet.domain.Message receivedMessage) {
        MessagesHasReceiver.add((pt.ist.sonet.domain.Agent)this, receivedMessage);
    }
    
    public void removeReceivedMessage(pt.ist.sonet.domain.Message receivedMessage) {
        MessagesHasReceiver.remove((pt.ist.sonet.domain.Agent)this, receivedMessage);
    }
    
    public java.util.List<pt.ist.sonet.domain.Message> getReceivedMessage() {
        return get$rl$receivedMessage();
    }
    
    public void set$receivedMessage(OJBFunctionalSetWrapper receivedMessage) {
        get$rl$receivedMessage().setFromOJB(this, "receivedMessage", receivedMessage);
    }
    
    public java.util.Iterator<pt.ist.sonet.domain.Message> getReceivedMessageIterator() {
        return get$rl$receivedMessage().iterator();
    }
    
    public int getSentMessageCount() {
        return get$rl$sentMessage().size();
    }
    
    public boolean hasAnySentMessage() {
        return (! get$rl$sentMessage().isEmpty());
    }
    
    public boolean hasSentMessage(pt.ist.sonet.domain.Message sentMessage) {
        return get$rl$sentMessage().contains(sentMessage);
    }
    
    public java.util.Set<pt.ist.sonet.domain.Message> getSentMessageSet() {
        return get$rl$sentMessage();
    }
    
    public void addSentMessage(pt.ist.sonet.domain.Message sentMessage) {
        MessageHasSender.add((pt.ist.sonet.domain.Agent)this, sentMessage);
    }
    
    public void removeSentMessage(pt.ist.sonet.domain.Message sentMessage) {
        MessageHasSender.remove((pt.ist.sonet.domain.Agent)this, sentMessage);
    }
    
    public java.util.List<pt.ist.sonet.domain.Message> getSentMessage() {
        return get$rl$sentMessage();
    }
    
    public void set$sentMessage(OJBFunctionalSetWrapper sentMessage) {
        get$rl$sentMessage().setFromOJB(this, "sentMessage", sentMessage);
    }
    
    public java.util.Iterator<pt.ist.sonet.domain.Message> getSentMessageIterator() {
        return get$rl$sentMessage().iterator();
    }
    
    public pt.ist.sonet.domain.AP getAp() {
        pt.ist.fenixframework.pstm.DataAccessPatterns.noteGetAccess(this, "ap");
        return ((DO_State)this.get$obj$state(false)).ap;
    }
    
    public void setAp(pt.ist.sonet.domain.AP ap) {
        APHasAgents.add((pt.ist.sonet.domain.Agent)this, ap);
    }
    
    public boolean hasAp() {
        return (getAp() != null);
    }
    
    public void removeAp() {
        setAp(null);
    }
    
    private java.lang.Long get$oidAp() {
        pt.ist.fenixframework.pstm.AbstractDomainObject value = ((DO_State)this.get$obj$state(false)).ap;
        return (value == null) ? null : value.getOid();
    }
    
    @jvstm.cps.ConsistencyPredicate
    public final boolean checkMultiplicityOfAp() {
        if (! hasAp()) return false;
        return true;
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
        AgentHasComments.add((pt.ist.sonet.domain.Agent)this, comments);
    }
    
    public void removeComments(pt.ist.sonet.domain.Comment comments) {
        AgentHasComments.remove((pt.ist.sonet.domain.Agent)this, comments);
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
        if (hasAnyReceivedMessage()) handleAttemptToDeleteConnectedObject();
        if (hasAnySentMessage()) handleAttemptToDeleteConnectedObject();
        if (hasAp()) handleAttemptToDeleteConnectedObject();
        if (hasAnyComments()) handleAttemptToDeleteConnectedObject();
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$username(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "USERNAME"), state);
        set$name(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "NAME"), state);
        set$password(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "PASSWORD"), state);
        set$rssi(pt.ist.fenixframework.pstm.ResultSetReader.readint(rs, "RSSI"), state);
        set$ip(pt.ist.fenixframework.pstm.ResultSetReader.readString(rs, "IP"), state);
        castedState.sonet = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_SONET");
        castedState.ap = pt.ist.fenixframework.pstm.ResultSetReader.readDomainObject(rs, "OID_AP");
    }
    protected dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("receivedMessage")) return MessagesHasReceiver;
        if (attrName.equals("sentMessage")) return MessageHasSender;
        if (attrName.equals("comments")) return AgentHasComments;
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("receivedMessage", MessagesHasReceiver);
        get$$relationList("sentMessage", MessageHasSender);
        get$$relationList("comments", AgentHasComments);
        
    }
    protected static class DO_State extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State {
        private java.lang.String username;
        private java.lang.String name;
        private java.lang.String password;
        private int rssi;
        private java.lang.String ip;
        private pt.ist.sonet.domain.SoNet sonet;
        private pt.ist.sonet.domain.AP ap;
        protected void copyTo(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.username = this.username;
            newCasted.name = this.name;
            newCasted.password = this.password;
            newCasted.rssi = this.rssi;
            newCasted.ip = this.ip;
            newCasted.sonet = this.sonet;
            newCasted.ap = this.ap;
            
        }
        
        // serialization code
        protected Object writeReplace() throws java.io.ObjectStreamException {
            return new SerializedForm(this);
        }
        
        protected static class SerializedForm extends pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State.SerializedForm {
            private static final long serialVersionUID = 1L;
            
            private java.lang.String username;
            private java.lang.String name;
            private java.lang.String password;
            private int rssi;
            private java.lang.String ip;
            private pt.ist.sonet.domain.SoNet sonet;
            private pt.ist.sonet.domain.AP ap;
            
            protected  SerializedForm(DO_State obj) {
                super(obj);
                this.username = obj.username;
                this.name = obj.name;
                this.password = obj.password;
                this.rssi = obj.rssi;
                this.ip = obj.ip;
                this.sonet = obj.sonet;
                this.ap = obj.ap;
                
            }
            
             Object readResolve() throws java.io.ObjectStreamException {
                DO_State newState = new DO_State();
                fillInState(newState);
                return newState;
            }
            
            protected void fillInState(pt.ist.fenixframework.pstm.OneBoxDomainObject.DO_State obj) {
                super.fillInState(obj);
                DO_State state = (DO_State)obj;
                state.username = this.username;
                state.name = this.name;
                state.password = this.password;
                state.rssi = this.rssi;
                state.ip = this.ip;
                state.sonet = this.sonet;
                state.ap = this.ap;
                
            }
            
        }
        
    }
    
}
