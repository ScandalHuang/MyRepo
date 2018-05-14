import React,{Component} from 'react';

function Item(props) {
    return (<option value={props.name}>{props.name}</option>);
}
class Dayselector extends Component{

    constructor(props){
        super(props);
        this.handlerSelectorChange=this.handlerSelectorChange.bind(this);

    }

    handlerSelectorChange(e){
        this.props.onChange(e);
    }

    render() {
        return (<select name={this.props.name} id={this.props.name} onChange={this.props.handlerSelectorChange}>
            <Item name="01"/>
            <Item name="02"/>
            <Item name="03"/>
            <Item name="04"/>
            <Item name="05"/>
            <Item name="06"/>
            <Item name="07"/>
            <Item name="08"/>
            <Item name="09"/>
            <Item name="10"/>
            <Item name="11"/>
            <Item name="12"/>
            <Item name="13"/>
            <Item name="14"/>
            <Item name="15"/>
            <Item name="16"/>
            <Item name="17"/>
            <Item name="18"/>
            <Item name="19"/>
            <Item name="20"/>
            <Item name="21"/>
            <Item name="22"/>
            <Item name="23"/>
            <Item name="24"/>
            <Item name="25"/>
            <Item name="26"/>
            <Item name="27"/>
            <Item name="28"/>
            <Item name="29"/>
            <Item name="30"/>
            <Item name="31"/>
        </select>);
    }
}
class Monthselector extends Component {
    constructor(props){
        super(props);
        this.handlerSelectorChange=this.handlerSelectorChange.bind(this);
    }
    handlerSelectorChange(e){
        this.props.onChange(e);
    }
    render(){
    return(<select  name={this.props.name} id={this.props.name} onChange={this.props.handlerSelectorChange}>
        <Item name="01"/>
        <Item name="02"/>
        <Item name="03"/>
        <Item name="04"/>
        <Item name="05"/>
        <Item name="06"/>
        <Item name="07"/>
        <Item name="08"/>
        <Item name="09"/>
        <Item name="10"/>
        <Item name="11"/>
        <Item name="12"/>
    </select>);
    }
}
class Yearselector extends Component{
    constructor(props){
        super(props);
        this.handlerSelectorChange=this.handlerSelectorChange.bind(this);
    }
    handlerSelectorChange(e){
        this.props.onChange(e);
    }
    render() {
        return (<select name={this.props.name} id={this.props.name} onChange={this.props.handlerSelectorChange}>
            <Item name="2016"/>
            <Item name="2017"/>
            <Item name="2018"/>
        </select>);
    }
}
class Datepicker extends Component{
  constructor(props){
    super(props);
    this.handlerSelectorChange=this.handlerSelectorChange.bind(this);
    this.handlerSubmit=this.handlerSubmit.bind(this);
  }
  handlerSelectorChange(e){
      this.props.handlerSelectorChange(e);
  }
  handlerSubmit(e){
      e.preventDefault();
      this.props.onSubmit(e);
  }
  render(){
      let str="";
      for(let i=0;i<1;i++)
          str+="<br/>";
    return(
        <form onSubmit={this.props.onSubmit}>
          <label>起始日期：</label>
          <Yearselector  name="startYear" handlerSelectorChange={this.props.handlerSelectorChange}/>
          <label>年</label>
          <Monthselector  name="startMonth" handlerSelectorChange={this.props.handlerSelectorChange}/>
          <label>月</label>
          <Dayselector name="startDay" handlerSelectorChange={this.props.handlerSelectorChange}/>
          <label>日</label>
            <div dangerouslySetInnerHTML={{__html:str}}/>
          <label>结束日期：</label>
          <Yearselector  name="endYear" handlerSelectorChange={this.props.handlerSelectorChange}/>
          <label>年</label>
          <Monthselector  name="endMonth" handlerSelectorChange={this.props.handlerSelectorChange}/>
          <label>月</label>
          <Dayselector name="endDay" handlerSelectorChange={this.props.handlerSelectorChange}/>
          <label>日</label>
          <input type="submit" value="submit"/>
        </form>
    );
  }
}

export default Datepicker;