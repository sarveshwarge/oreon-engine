package engine.gpubuffers;

import static org.lwjgl.opengl.GL30.GL_RGBA32F;
import static org.lwjgl.opengl.GL30.GL_DEPTH_COMPONENT32F;
import static org.lwjgl.opengl.GL30.GL_RENDERBUFFER;
import static org.lwjgl.opengl.GL30.glBindRenderbuffer;
import static org.lwjgl.opengl.GL30.glFramebufferTexture2D;
import static org.lwjgl.opengl.GL32.glFramebufferTexture;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glRenderbufferStorage;
import static org.lwjgl.opengl.GL30.glFramebufferRenderbuffer;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDrawBuffer;


public class Framebuffer {
	
	private int id;
	private int colorbuffer;
	private int depthbuffer;
	
	public Framebuffer(){
		
		id = glGenFramebuffers();
	}
	
	public void bind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, id);
	}
	
	public void unbind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	public void setDrawBuffer(int i)
	{
		glDrawBuffer(GL_COLOR_ATTACHMENT0 + i);
	}
	
	public void colorBufferAttachment(int x, int y, int i)
	{
		colorbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, colorbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_RGBA32F, x, y);
		glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_RENDERBUFFER, colorbuffer);
	}
	

	public void colorTextureAttachment(int texture, int i)
	{
		glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_COLOR_ATTACHMENT0 + i, GL_TEXTURE_2D, texture, 0);
	}
	
	public void depthbufferAttachment(int x, int y)
	{
		depthbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32F, x, y);
		glFramebufferRenderbuffer(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthbuffer);
	}
	
	public void depthTextureAttachment(int texture)
	{
		glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture, 0);
	}
	
	public void checkStatus()
	{
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
		{
			System.err.println("Framebuffer creation failed");
			System.exit(1);
		}
	}
	
	public int getId()
	{
		return id;
	}

	public int getColorbuffer() {
		return colorbuffer;
	}

	public void setColorbuffer(int colorbuffer) {
		this.colorbuffer = colorbuffer;
	}

	public int getDepthbuffer() {
		return depthbuffer;
	}

	public void setDepthbuffer(int depthbuffer) {
		this.depthbuffer = depthbuffer;
	}
}