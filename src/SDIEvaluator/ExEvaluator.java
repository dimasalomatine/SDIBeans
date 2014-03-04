package SDIEvaluator;
import java.util.*;

public class ExEvaluator extends TypesT
{
  private String exp="";  // points to the expression
  private List exp_ptr=new ArrayList(); // holds all tokens
  private int curposin=0;
  private String token=""; // holds current token
  private int tok_type=UNDEFTOK;  // holds curent token's type
  
  private List vars=new ArrayList(); // holds variables's names and values
  // holds operators's names oc and tag
  /*private Object []op={
			new Object[]{new String("+"),new String("plus"),new Integer(std_PLUS)},
			new Object[]{new String("-"),new String("minus"),new Integer(std_MINUS)},
			new Object[]{new String("*"),new String("mul"),new Integer(std_MUL)},
			new Object[]{new String("/"),new String("div"),new Integer(std_DIV)},
			new Object[]{new String("^"),new String("pow"),new Integer(std_POW)},
			new Object[]{new String("%"),new String("mod"),new Integer(std_MOD)},
			new Object[]{new String("="),new String("set"),new Integer(std_SET)},
			new Object[]{new String("("),new String("left bracket"),new Integer(std_LB)},
			new Object[]{new String(")"),new String("right bracket"),new Integer(std_RB)},
			new Object[]{new String("sin"),new String("sinus"),new Integer(std_SIN)},
			new Object[]{new String("asin"),new String("arc sinus"),new Integer(std_ASIN)},
			new Object[]{new String("cos"),new String("cosinus"),new Integer(std_COS)},
			new Object[]{new String("acos"),new String("arc cosinus"),new Integer(std_ACOS)},
			new Object[]{new String("tg"),new String("tangens"),new Integer(std_TAN)},
			new Object[]{new String("atg"),new String("arc angens"),new Integer(std_ATAN)},
			new Object[]{new String("ln"),new String("log on base e"),new Integer(std_LN)},
			new Object[]{new String("lg"),new String("log on base LB is user base"),new Integer(std_LG)},
			new Object[]{new String("log"),new String("log on base 10"),new Integer(std_LOG)},
			new Object[]{new String("abs"),new String("absolute value | exp |"),new Integer(std_ABS)},
			new Object[]{new String("floor"),new String("floor"),new Integer(std_FLOOR)},
			new Object[]{new String("ceil"),new String("ceil"),new Integer(std_CEIL)}
			}; 
*/
  private Object []op={
			new Object[]{"+","plus",new Integer(std_PLUS)},
			new Object[]{"-","minus",new Integer(std_MINUS)},
			new Object[]{"*","mul",new Integer(std_MUL)},
			new Object[]{"/","div",new Integer(std_DIV)},
			new Object[]{"^","pow",new Integer(std_POW)},
			new Object[]{"%","mod",new Integer(std_MOD)},
			new Object[]{"=","set",new Integer(std_SET)},
			new Object[]{"(","left bracket",new Integer(std_LB)},
			new Object[]{")","right bracket",new Integer(std_RB)},
			new Object[]{"sin","sinus",new Integer(std_SIN)},
			new Object[]{"asin","arc sinus",new Integer(std_ASIN)},
			new Object[]{"cos","cosinus",new Integer(std_COS)},
			new Object[]{"acos","arc cosinus",new Integer(std_ACOS)},
			new Object[]{"tg","tangens",new Integer(std_TAN)},
			new Object[]{"atg","arc angens",new Integer(std_ATAN)},
			new Object[]{"ln","log on base e",new Integer(std_LN)},
			new Object[]{"lg","log on base LB is user base",new Integer(std_LG)},
			new Object[]{"log","log on base 10",new Integer(std_LOG)},
			new Object[]{"abs","absolute value | exp |",new Integer(std_ABS)},
			new Object[]{"floor","floor",new Integer(std_FLOOR)},
			new Object[]{"ceil","ceil",new Integer(std_CEIL)}
			}; 
// Constructor
public ExEvaluator()
{
  init_locals();
}
public ExEvaluator(String texp)
{
  init_locals();
  init_expression(texp);
}
void init_expression(String texp)
{
	exp = removedoublespaces(texp);
	exp_ptr.clear();
	StringTokenizer st=new StringTokenizer(exp);
	while(st.hasMoreTokens())exp_ptr.add(st.nextToken());
	curposin=0;
}
void reset_to_first()
{
	curposin=0;
}
void init_locals()
{
  int i;
  Object []tvars={
  	new Object[]{"e",new DPNumber(Math.E),new Integer(std_SYSTEM)},
  	new Object[]{"pi",new DPNumber(Math.PI),new Integer(std_SYSTEM)},
  	new Object[]{"LB",new DPNumber(0.0),new Integer(std_USER)}
  };
  vars.clear();
  //add system predefined variables
  for(i=0; i<tvars.length; i++)
  {
  	vars.add(tvars[i]);
  }
  //add freq used variables 
  add_var("x",0.0);
  add_var("y",0.0);
  add_var("z",0.0);
  //add some variables to play with x1,x2 ...
  for(i=1; i<4; i++)
  {
  	 Object t=new Object[]{"x"+i,new DPNumber(0.0),new Integer(std_USER)};
  	 vars.add(t);
  }	
}
private String warp_with_other(String str,String find,String other)
{
 int start=0,found=0;
 String ns="";
 while(found!=-1)
	{
		found=str.indexOf(find,start);
		if(found!=-1)
		{
                    /*
			ns=str.substring(0,found)+other;
			ns+=find+other;
			ns+=str.substring(found+find.length());
                     */
                ns=str.substring(0,found)+other
                   +find+other
                    +str.substring(found+find.length());
	    	start=found+find.length()+other.length()*2+1;
	    	str=ns;
		}
	}
 return str.trim();	
}
public String removedoublespaces(String str)
{
 int start=0,found=0;
 String ns="";
 String find="  ";
 while(found!=-1)
	{
		found=str.indexOf(find,start);
		if(found!=-1)
		{
			ns=str.substring(0,found)
                            +" "+str.substring(found+find.length());
	    	start=found+1;
	    	str=ns;
		}
	}
 return str.trim();	
}
public String corect_exp(String str)
{
	int i;
	// find op and replace
	for(i=0;i<op.length;i++)
	{
		Object []t=(Object [])op[i];
		str=warp_with_other(str,(String)t[0]," ");
	}
	// find ocurence of vars and replace
	for(i=0;i<vars.size();i++)
	{
		Object []t=(Object [])vars.get(i);
		str=warp_with_other(str,(String)t[0]," ");
	}
	return str;
}
// Parser entry point if expresion already tokenized and loaded.
public double eval_exp()
{
  DPNumber result=new DPNumber(0.0);

  reset_to_first();

  get_token();
  if(token.length()==0) 
  {
    serror(NOEXP); // no expression present
    return 0.0;
  }
  eval_exp1(result);
  if(token.length()>0)serror(SERROR); // last token must be null
  return result.num;
}
// Parser entry point.
public double eval_exp(String exp)
{
  //System.out.println("exp");
  DPNumber result=new DPNumber(0.0);

  init_expression(exp);

  get_token();
  if(token.length()==0) 
  {
    serror(NOEXP); // no expression present
    return 0.0;
  }
  eval_exp1(result);
  if(token.length()>0)serror(SERROR); // last token must be null
  return result.num;
}
// Process an assignment.
private void eval_exp1(DPNumber result)
{
  //System.out.println("exp1");
  int slot;
  int ttok_type;
  String temp_token;

  if(tok_type==VARIABLE) 
  {
    // save old token
    temp_token= token;
    ttok_type = tok_type;

	slot=isvar(token);

    get_token();
    if(getopcode(token)!=std_SET) 
    {
      putback(); // return current token
      // restore old token - not assignment
      token= temp_token;
      tok_type = ttok_type;
    }
    else {
      get_token(); // get next part of exp
      eval_exp2(result);
      DPNumber thevar=find_var_ref(slot);
      thevar.num = result.num;
      return;
    }
  }

  eval_exp2(result);
}
// Add or subtract two terms.
private void eval_exp2(DPNumber result)
{
  //System.out.println("exp2");
  String op;
  DPNumber temp=new DPNumber(0.0);

  eval_exp3(result);
  while(getopcode(op = token)==std_PLUS || getopcode(op)==std_MINUS) 
  {
    get_token();
    eval_exp3(temp);
    switch(getopcode(op)) 
    {
      case std_MINUS:
        result.num = result.num - temp.num;
        break;
      case std_PLUS:
        result.num = result.num + temp.num;
        break;
    }
  }
}
// Multiply or divide two factors.
private void eval_exp3(DPNumber result)
{
  //System.out.println("exp3");
  String op;
  DPNumber temp=new DPNumber(0.0);

  eval_exp4(result);
  while(getopcode(op = token)==std_MUL || getopcode(op)==std_DIV || getopcode(op)==std_MOD) 
  {
    get_token();
    eval_exp4(temp);
    switch(getopcode(op))
    {
      case std_MUL:
        result.num = result.num * temp.num;
        break;
      case std_DIV:
        if(temp.num==0.0) serror(DIVZERO); // division by zero attempted
        else result.num = result.num / temp.num;
        break;
      case std_MOD:
        result.num = (int) result.num % (int) temp.num;
        break;
    }
  }
}
// Process an advanced calculations.
private void eval_exp4(DPNumber result)
{
  //System.out.println("exp4");
  DPNumber temp=new DPNumber(0.0);
  eval_exp5(result);
  if(getopcode(token)==std_POW)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.pow(result.num,temp.num);
  }else if(getopcode(token)==std_SIN)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.sin(temp.num);
  }else if(getopcode(token)==std_ASIN)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.asin(temp.num);
  }else if(getopcode(token)==std_COS)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.cos(temp.num);
  }else if(getopcode(token)==std_ACOS)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.cos(temp.num);
  }else if(getopcode(token)==std_TAN)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.tan(temp.num);
  }else if(getopcode(token)==std_ATAN)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.atan(temp.num);
  }else if(getopcode(token)==std_LN)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.log(temp.num);
  }else if(getopcode(token)==std_LG)
   {
    get_token();
    eval_exp4(temp);
    //conversion to log in base b
    // res= ln num\ln b
    result.num = Math.log(temp.num)/Math.log(get_var("LB"));
  }else if(getopcode(token)==std_LOG)
   {
    get_token();
    eval_exp4(temp);
    //conversion to log 10
    // res= ln num\ln 10
    result.num = Math.log(temp.num)/Math.log(10.0);
  }else if(getopcode(token)==std_FLOOR)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.floor(temp.num);
  }else if(getopcode(token)==std_CEIL)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.ceil(temp.num);
  }else if(getopcode(token)==std_ABS)
   {
    get_token();
    eval_exp4(temp);
    result.num = Math.abs(temp.num);
  }
}
// Evaluate a unary + or -.
private void eval_exp5(DPNumber result)
{
  //System.out.println("exp5");
  String  op="";
  if((tok_type == OPERATOR) && getopcode(token)==std_PLUS || getopcode(token)==std_MINUS) 
  {
    op = token;
    get_token();
  }
  eval_exp6(result);
  if(getopcode(op)==std_MINUS) result.num = -result.num;
}
// Process a parenthesized expression.
private void eval_exp6(DPNumber result)
{
  //System.out.println("exp6");
  if(getopcode(token)==std_LB) 
  {
    get_token();
    eval_exp2(result);
    if(getopcode(token)!=std_RB)
      serror(PARENS);
    get_token();
  }
  else atom(result);
}
// Get the value of a number or a variable.
private void atom(DPNumber result)
{
  //System.out.println("atom");
  switch(tok_type) 
  {
    case VARIABLE:
      result.num = find_var(token);
      get_token();
      return;
    case NUMBER:
      result.num = Double.parseDouble(token);
      get_token();
      return;
    default:
      serror(SERROR);
  }
}
// Return a token to the input stream.
//logicaly
private void putback()
{
  curposin--;
}
// Obtain the next token.
private void get_token()
{

  tok_type = UNDEFTOK;
  token="";
  if(curposin>=exp_ptr.size()) return; // at end of expression

  if(getopcode((String)exp_ptr.get(curposin))!=0)
  {
    tok_type = OPERATOR;
    // advance to next token
    token = (String)exp_ptr.get(curposin);
    curposin++;
  }
  else if(isvar((String)exp_ptr.get(curposin))>=0) 
  {
    token = (String)exp_ptr.get(curposin);
    tok_type = VARIABLE;
    curposin++;
  }
  else if(isdigit((String)exp_ptr.get(curposin)))
  {
    token = (String)exp_ptr.get(curposin);
    curposin++;
    tok_type = NUMBER;
  }
  //System.out.println("Curent in get is : "+token);
}
// Return true if s is a digit but actually if is it can be converted to number.
public boolean isdigit(String s)
{
    Double t;
 try
 {
    t=Double.valueOf(s);
 }catch(NumberFormatException e)
 {
  return false;	
 }
 return true;
}
// Return true if s is a delimiter.
public boolean isdelim(String s)
{
	for(int i=0;i<op.length;i++)
 {
 	Object []t=(Object[])op[i];
 	if(((String)t[0]).equals(s))return true;
 }
 return false;
}
public int getopcode(String s)
{
	for(int i=0;i<op.length;i++)
 {
 	Object []t=(Object[])op[i];
 	if(((String)t[0]).equals(s))return ((Integer)t[2]).intValue();
 }
 return 0;
}
public String getopcode(int oc)
{
	for(int i=0;i<op.length;i++)
 {
 	Object []t=(Object[])op[i];
 	if(((Integer)t[2]).intValue()==oc)return (String)t[0];
 }
 return "";
}
public String getopcodedesc(String s)
{
	for(int i=0;i<op.length;i++)
 {
 	Object []t=(Object[])op[i];
 	if(((String)t[0]).equals(s))return (String)t[1];
 }
 return "unknown";
}
public String getopcodedesc(int oc)
{
	for(int i=0;i<op.length;i++)
 {
 	Object []t=(Object[])op[i];
 	if(((Integer)t[2]).intValue()==oc)return (String)t[1];
 }
 return "unknown";
}
private int isvar(String s)
{
	for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(t[0].equals(s))return i;
 }
 return -1;
}
// Return the value of a variable.
public double find_var(String s)
{
 for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(t[0].equals(s))return ((DPNumber)t[1]).num;
 }
 serror(SERROR);
 return 0.0;
}
// Return the ref of a variable.
/*
private DPNumber find_var_ref(String s)
{
 for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(t[0].equals(s))return (DPNumber)t[1];
 }
 serror(SERROR);
 return null;
}
 */
// Return the ref of a variable.
private DPNumber find_var_ref(int slot)
{
 if(slot>=0&&slot<vars.size())
 {
 	Object []t=(Object[])vars.get(slot);
 	return (DPNumber)t[1];
 }
 serror(SERROR);
 return null;
}
// Rename the variable.
public void rename_var(String old,String newname)
{
 for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(t[0].equals(old))
 	{
 		if(((Integer)t[2]).intValue()!=std_SYSTEM)t[0]=newname;
 		else serror(SYSVARERROR);
 		return;
 	}
 }
 serror(SERROR);
}
public double get_var(String s)
{
 for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(t[0].equals(s))return ((DPNumber)t[1]).num;
 }
 serror(SERROR);
 return 0.0;
}
public void set_var(String varn,double newval)
{
 for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(t[0].equals(varn))
 	{
 		if(((Integer)t[2]).intValue()!=std_SYSTEM)((DPNumber)t[1]).num=newval;
 		else serror(SYSVARERROR);
 		return;
 	}
 }
 serror(SERROR);
}
public void add_var(String varn,double val)
{
 boolean uniq=true;
 Object []t=null;
 for(int i=0;i<vars.size();i++)
 {
 	t=(Object[])vars.get(i);
 	if(t[0].equals(varn))
 	{
 		if(((Integer)t[2]).intValue()==std_SYSTEM)
 		{
 		serror(SYSVARERROR);
 		return;
 		}
 		uniq=false;
 		break;
 	}
 }
 if(uniq==true)
 {
  //Object tv=new Object[]{new String(varn),new DPNumber(val),new Integer(std_USER)};
  Object tv=new Object[]{varn,new DPNumber(val),new Integer(std_USER)};
  vars.add(tv);
  return;	
 }
 serror(SERROR);
}
public void print_var(String s)
{
 for(int i=0;i<vars.size();i++)
 {
 	Object []t=(Object[])vars.get(i);
 	if(s.equals("ALL"))
 	{
 		System.out.println("Variable: "+t[0]+"="+((DPNumber)t[1]).num);
 	}
 	else if(t[0].equals(s))
 	{
 		System.out.println("Variable: "+t[0]+"="+((DPNumber)t[1]).num);
 		return;
 	}
 }
}
}