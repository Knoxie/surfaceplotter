package com.googlecode.surfaceplotter;

import java.awt.Color;
/**

 * @author Eric
 * @date jeudi 8 avril 2004 15:45:40
 */
public class ColorModelSet
{
	
	public static float RED_H=0.941896f;
	public static float RED_S=0.7517241f;
	public static float RED_B=0.5686275f;
	
	public static float GOLD_H=0.1f;
	public static float GOLD_S=0.9497207f;
	public static float GOLD_B=0.7019608f;
	
	
	protected ColorModel dualshade;
	protected ColorModel grayscale;
	protected ColorModel spectrum;
	protected ColorModel fog;
	protected ColorModel opaque;

	protected ColorModel alt_dualshade;
	protected ColorModel alt_grayscale;
	protected ColorModel alt_spectrum;
	protected ColorModel alt_fog;
	protected ColorModel alt_opaque;
	
	protected Color lineColor=Color.DARK_GRAY;
	protected  Color lineboxColor=Color.getHSBColor(0f,0f,0.5f);
	protected  Color lightColor=Color.WHITE;
	// Color(192,220,192); existing
	protected  Color boxColor=Color.getHSBColor(0f,0f,0.95f);//Color.getHSBColor(226f/240f,145f/240f,1f);
	
	public ColorModelSet()
	{
		/*
		float[] f = Color.RGBtoHSB(255,255,255, new float[4]);
		System.out.print("DP_RED=(");
		for (int i=0;i<3;i++) { System.out.println((i==0?"":",")+f[i]);}
		*/
		
		dualshade= new ColorModel(ColorModel.DUALSHADE,
			RED_H	,RED_S	,RED_B	,			0.4f,1f);
		grayscale= new ColorModel(ColorModel.DUALSHADE,
			0f		,0f		,0f	,				0f,1f);
		spectrum= new ColorModel(ColorModel.SPECTRUM,
			0f		,1f		,1f	,				0f,.6666f);
		fog= new ColorModel(ColorModel.FOG,
			RED_H	,RED_S		,RED_B,			 0f,1f);
		opaque= new ColorModel(ColorModel.OPAQUE,
			0f,		0.2f		,0.7f,				0f,0f);

		
		
		alt_dualshade= new ColorModel(ColorModel.DUALSHADE,
			GOLD_H	,GOLD_S	,GOLD_B	,		0.4f,1f);
		alt_grayscale= new ColorModel(ColorModel.DUALSHADE,
			0f		,0f		,0f	,				0f,1f);
		alt_spectrum= new ColorModel(ColorModel.SPECTRUM,
			0f		,1f		,0.5f	,				0f,.6666f);
		alt_fog= new ColorModel(ColorModel.FOG,
			GOLD_H	,0f		,GOLD_B,			 0f,1f);
		alt_opaque= new ColorModel(ColorModel.OPAQUE,
			0.6f		,0.2f		,0.7f					,0f,0f);
		
	}
	
	protected int color_mode=0;
	public void setPlotColor(int v)	
	{
		this.color_mode=v;
	}
	protected int plot_mode=0;
	public void setPlotType(int type)
	{
		this.plot_mode=type;
	}
	
	public Color getBackgroundColor(){return lightColor;}
	public Color getLineBoxColor() {return lineboxColor;}
	public Color getBoxColor() {return boxColor;}
	public Color getLineColor(){return lineColor;}
	public Color getTextColor() {return lineColor;}
	
	public Color getLineColor(int curve, float z)
	{
		//return Color.BLUE;
		/**/
		if (plot_mode==SurfaceModel.PLOT_TYPE_WIREFRAME)
		{
			return Color.BLACK;
		}
		if (
			color_mode==SurfaceModel.PLOT_COLOR_GRAYSCALE	|| 
			color_mode==SurfaceModel.PLOT_COLOR_SPECTRUM||
			color_mode==SurfaceModel.PLOT_COLOR_DUALSHADE)
		return grayscale.getPolygonColor(1-z);
		else return Color.DARK_GRAY;
		
		/*
		Color c= getPolygonColor(curve, z);
		float[] f= c.getComponents(new float[4]);
		float ff=f[2];
		if (ff<0.5f) ff=1f;
		if (ff<0) ff++;
		*/
		//return Color.getHSBColor(f[0],f[1],ff);
		/**/
	}
	
	public Color getPolygonColor(int curve, float z)
	{
		if (curve==1) return getFirstPolygonColor(z);
		if (curve==2) return getSecondPolygonColor(z);
		return Color.blue;
	}
	
	public Color getFirstPolygonColor(float z)
	{
		//contour,density  plot does not fit with opaque color 
		if(plot_mode==SurfaceModel.PLOT_TYPE_CONTOUR ||plot_mode==SurfaceModel.PLOT_TYPE_DENSITY)
		{
			if (color_mode==SurfaceModel.PLOT_COLOR_OPAQUE)
				return dualshade.getPolygonColor(z);
		}
		
		switch ( color_mode)
		{
		case SurfaceModel.PLOT_COLOR_OPAQUE	:return opaque.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_GRAYSCALE	:return grayscale.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_SPECTRUM	:return spectrum.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_DUALSHADE	:return dualshade.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_FOG		:return fog.getPolygonColor(z);
		default: return Color.blue;
		}
	}
	
	public Color getSecondPolygonColor(float z)
	{
		//contour,density  plot does not fit with opaque color 
		if(plot_mode==SurfaceModel.PLOT_TYPE_CONTOUR ||plot_mode==SurfaceModel.PLOT_TYPE_DENSITY)
		{
			if (color_mode==SurfaceModel.PLOT_COLOR_OPAQUE)
				return alt_dualshade.getPolygonColor(z);
		}
		switch ( color_mode)
		{
		case SurfaceModel.PLOT_COLOR_OPAQUE	:return alt_opaque.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_GRAYSCALE	:return alt_grayscale.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_SPECTRUM	:return alt_spectrum.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_DUALSHADE	:return alt_dualshade.getPolygonColor(z);
		case SurfaceModel.PLOT_COLOR_FOG		:return alt_fog.getPolygonColor(z);
		default: return Color.blue;
		}
	}
	
	/*
	protected float dualshadeColorFirstHue=0.2f;//0.7f;//0.1f;//0.941f; // first curve hue color
	protected float dualshadeColorSecondHue=0.7f;
	protected float dualshadeSaturation = 0.9125f;//0.604f; // 
	protected float dualshadeOffset=0.3f;
	protected float whiteblack = 0.3f;
	
	
	*/
	
}//end of class