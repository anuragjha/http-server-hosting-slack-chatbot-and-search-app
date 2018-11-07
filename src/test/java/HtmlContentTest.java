import org.junit.Test;

import htmlGenerator.CreateContent;

public class HtmlContentTest {

	CreateContent cc = new CreateContent();
	
	/*
	 using console output to check on - https://jsonformatter.org/html-validator
	*/
	
	@Test
	public void buildReviewSearchForm() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildReviewSearchForm());
		
	}
	
	@Test
	public void buildFindForm() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildFindForm()); 
		
	}
		
	@Test
	public void buildChatForm() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildChatForm());
	}
	
	
	@Test
	public void buildChatFormAfterFail() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildChatFormAfterFail());
	}
	
	
	@Test
	public void buildChatFormAfterSuccess() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildChatFormAfterSuccess());
	}
	
	@Test
	public void buildContent400() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildContent400());
	}
	
	
	@Test
	public void buildContent404() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildContent404());
	}
	
	
	@Test
	public void buildContent405() {
		CreateContent cc = new CreateContent();
		System.out.println("\n" + cc.buildContent405());
	}
	
}
