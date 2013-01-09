package cdi.example.palindrome;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import cdi.example.palindrome.bindings.PalindromeServiceSync;


@Named
@ConversationScoped
// Bean declaring a passivating scope must be passivation capable
public class PalindromeConversation implements Serializable {
    
	private static final long serialVersionUID = 1L;

	// Context: Injecting an application scoped bean into a conversation scoped bean.
	@Inject @PalindromeServiceSync 
	private Palindrome palindromeBean; 

	@Inject
	Conversation conversation;
	
	private String word;
    private Boolean palindrome;
    // Within a conversation (i.e. unit of work) we can keep track of the previous attempts 
    private List<String> previousWords;

    public void checkPalindrome() {
    	// Conversations are transient by default. We promote this to long-running.
    	if ( conversation.isTransient() )
        	conversation.begin();
    	this.palindrome = palindromeBean.isPalindrome(word);
        if ( this.previousWords == null )
        	this.previousWords = new ArrayList<String>();
        this.previousWords.add(word);
    }

    public Boolean getPalindrome() {
        return palindrome;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public String getWord() {
        return word;
    }

	public String getPrevious() {
		StringBuilder previous = new StringBuilder();
		int i = 0;
		if ( previousWords != null ) {
			for ( String s : previousWords ) {
				if ( i > 0 )
					previous.append(", ");
				previous.append("[").append(s).append("]");
				i++;
			}
		}
		return previous.toString();
	}

}
