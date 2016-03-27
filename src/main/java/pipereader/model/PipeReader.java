package pipereader.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import event.ApplicationEvent;
import event.ApplicationEventListener;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import util.*;

import javax.sql.DataSource;

public class PipeReader implements Runnable, PipeEventListener, UIEventListener, ApplicationEventListener {

	/**
	 * @param args
	 */
	private static final Logger log = Logger.getLogger(PipeReader.class);

	private DataSource ds;
	private Connection conn;
	private String type;
	private OracleCallableStatement stmt;
	private Thread pThread;
	private Boolean stop;
	private Map<String, PipeState> pipes;
	private String guery;

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setQuery(String guery) {
		this.guery = guery;
	}

	@Deprecated
	public void notify(PipeEvent e) {
		if (e.getAction() == 0){
			addPipe(e.getPipeName());
		}
		else {
			removePipe(e.getPipeName());
		}
	}


	@Deprecated
	@Override
	public void notify(UIEvent uiEvent) {
		String action = (String)uiEvent.getArgs().get("action");
		String pipeName =  (String)uiEvent.getArgs().get("name");
		log.debug("Событие " +  action + "(name:"+pipeName+")");

		switch (action.toUpperCase()) {
			case "ADD":
				addPipe(pipeName);
				break;
			case "REMOVE":
				removePipe(pipeName);
				break;
			default:
				break;
		}
	}

	@Override
	public void notify(ApplicationEvent applicationEvent) {
		PipeEvent e = (PipeEvent)applicationEvent;
		switch (e.getState()) {
			case STARTWRITING:
				addPipe(e.getPipeName());
				break;
			case STOPWRITING:
				removePipe(e.getPipeName());
				break;
			default:
				break;
		}
	}

	public PipeReader (){};

	public PipeReader (DataSource ds, String type) throws SQLException {
		this(ds);
		this.type = type;
	}

	public PipeReader (DataSource ds) throws SQLException {
		this.pipes = new ConcurrentHashMap<String, PipeState>();
		this.stop = false;
		this.ds = ds;
		this.conn = ds.getConnection();
	//	log.debug("Подключились к БД " + this.conn.getMetaData().getURL());
	//	this.start();
	}

	public void start(){
		InputStream in = getClass().getResourceAsStream("/resources/"+ this.guery);
		log.debug(System.getProperty("java.class.path"));
		String c = null;
		try {
			c = IOUtils.toString(in, "UTF-8");
		} catch (IOException e) {
			log.error(LogUtil.getStackTrace(e));
		}
		try {
			stmt = (OracleCallableStatement)conn.prepareCall(c);
			if (this.type != null ) {
				stmt.registerOutParameter(2, OracleTypes.ARRAY, this.type);
			}
			else {
				stmt.registerOutParameter(2, OracleTypes.VARCHAR);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pThread = new Thread(this);
		pThread.setDaemon(true);
		pThread.start();
	}

	public void close() throws SQLException{
		this.stmt.close();
	}

	public void addPipe(String pipe) {
		log.debug("addPipe: " + pipe);
		if (pipe != null)	{
			this.pipes.put(pipe, PipeState.READY);
		}
	}
	public void removePipe(String pipe) {
		log.debug("removePipe: " + pipe);
		if (pipe != null)	{
			this.pipes.put(pipe, PipeState.CLOSE);
		}
	}

	public void stopRead() {
		this.stop = true;
	}

	public void read() throws SQLException, InterruptedException {
		while (!this.stop) {
			log.debug("читаем...");
			Iterator<String> itr = pipes.keySet().iterator();
			while (itr.hasNext()) {
				String pipe = itr.next();
				stmt.setString(1, pipe);
				stmt.execute();
				if (this.type != null ) {
					String[] s = (String[])stmt.getARRAY(2).getArray();
					log.info("READING PIPE " + pipe);
					for (int i=0; i<s.length; i++) {
						log.info(s[i]);
					}
				}
				else {
					String c = stmt.getString(2);
					if (c != null) {
						log.info("READING PIPE " + pipe + "\r\n" + c);
					}
				}
				if (pipes.get(pipe)==PipeState.CLOSE) {pipes.remove(pipe);}
			}
			TimeUnit.MILLISECONDS.sleep(1000);
		}
	}
	
	@Override
	public void run() {
		try {
			this.read();
		} catch (Exception e) {
			log.error(LogUtil.getStackTrace(e));
		}
	}
}
