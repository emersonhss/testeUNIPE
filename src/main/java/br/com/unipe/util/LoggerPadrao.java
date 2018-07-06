package br.com.unipe.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;

public class LoggerPadrao {
	
	private static Logger logErro = LoggerFactory.getLogger("erros");
	private static Logger logInfo = LoggerFactory.getLogger("informacao");
	private static Logger logInfoStartApplication = LoggerFactory.getLogger("start_application");
	private static Logger logDebug = LoggerFactory.getLogger("depuracao");
	private static Logger logTransacao = LoggerFactory.getLogger("transacao");
	private static SentryClient sentry;
	
	static {
		Sentry.init("https://1a1675e5779e490095a5f864b214044d@sentry.io/1238967");
		sentry = SentryClientFactory.sentryClient();
		
		// Retrieve the current context.
        Context context = sentry.getContext();

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("Iniciando a action do usuário...").build());

        // Set the user in the current context.
        context.setUser(new UserBuilder().setEmail("1a1675e5779e490095a5f864b214044d@sentry.io").build());
	}
	
	public static void info(String mensagem, Object ... args){
		logInfo.info(mensagem, args);
		sentry.sendMessage(mensagem);
	}
	
	public static void info(String mensagem){
		logInfo.info(mensagem);
		sentry.sendMessage(mensagem);
	}
	
	public static void transacao(String mensagem){
		logTransacao.info("loggerTransacao - "+mensagem);
		sentry.sendMessage("loggerTransacao - "+mensagem);
	}
	
	public static void debug(String mensagem, Object ... args){
		logDebug.debug(mensagem, args);
		sentry.sendMessage("debug - "+mensagem);
	}
	
	public static void debug(String mensagem, long time){
		long timeMilis = System.currentTimeMillis() - time;
		logDebug.debug(mensagem+ " - "+ timeMilis +" ms");
		sentry.sendMessage("debug - "+mensagem+ " - "+ timeMilis +" ms");
	}
	

	public static void error(String mensagem, Exception e) {
		logErro.error(mensagem, e);
		sentry.sendException(e);
	}

	public static void error(String string) {
		logErro.error(string);
		sentry.sendMessage("error - " + string);
	}
	
	public static void info(String mensagem, long time, Object ... args){
		long timeMilis = System.currentTimeMillis() - time;
		logInfo.info(mensagem+" - "+ timeMilis +" ms", args);
		sentry.sendMessage(mensagem+ " - "+ timeMilis +" ms");
	}
	
	public static void info(String mensagem, long time){
		long timeMilis = System.currentTimeMillis() - time;
		logInfo.info(mensagem+ " - "+ timeMilis +" ms");
		sentry.sendMessage(mensagem+ " - "+ timeMilis +" ms");
	}
	
	public static void transacao(String mensagem, long time){
		long timeMilis = System.currentTimeMillis() - time;
		logTransacao.info("loggerTransacao - "+mensagem+" - "+timeMilis+" ms");
		sentry.sendMessage("loggerTransacao - "+mensagem+ " - "+ timeMilis +" ms");
	}
	
	public static void debug(String mensagem, long time, Object ... args){
		long timeMilis = System.currentTimeMillis() - time;
		logDebug.debug(mensagem+" - "+timeMilis+" ms", args);
		sentry.sendMessage("debug - "+mensagem+ " - "+ timeMilis +" ms");
	}

	public static void error(String mensagem, Exception e, long time) {
		logErro.error(mensagem+" - "+(System.currentTimeMillis()-time)+" ms", e);
		sentry.sendException(e);
	}

	public static void error(String string, long time) {
		long timeMilis = System.currentTimeMillis() - time;
		logErro.error(string+" - "+timeMilis+" ms");
		sentry.sendMessage("error - "+string+ " - "+ timeMilis +" ms");
	}
	
	public static void startApplication(String mensagem, Object ... args){
		logInfoStartApplication.info(mensagem, args);
		sentry.sendMessage(mensagem);
	}
}
