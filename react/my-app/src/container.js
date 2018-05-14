import React,{Component} from 'react';
import axios from 'axios';
import Datepicker from "./App";
import Table from './table';


class Container extends Component {
    constructor(props) {
        super(props);
        this.handlerSelectorChange=this.handlerSelectorChange.bind(this);
        this.handlerSubmit=this.handlerSubmit.bind(this);
        this.state = {
            startDay: "01",
            startMonth: "01",
            startYear: "2016",
            endDay: "01",
            endMonth: "01",
            endYear: "2016",
            array: [{
                CARD_ID:"CARD_ID",
                PDATE:"PDATE",
                PFIRST:"PFIRST",
                PLAST:"PLAST",
                EMPWNO:"EMPWNO",
                EMPCNM:"EMPCNM",
                DEPNAM:"DEPNAM",
                DEPTCNM:"DEPTCNM"
            }]
        }
    }

    componentDidMount(){
        axios.defaults.baseURL='http://localhost:52536/api/Record';
        axios.get('')
            .then(res=>{
                const array=JSON.parse(res.data);
                this.setState({array});
                }
            )
    }

    handlerSelectorChange(e){
        e.preventDefault();
        const target = e.target;
        const value = target.value;
        const name = target.name;
        this.setState({[name]: value});
    }

    handlerSubmit(e) {
        e.preventDefault();
        var time={
            "startYear":this.state.startYear,
            "startMonth":this.state.startMonth,
            "startDay":this.state.startDay,
            "endYear":this.state.endYear,
            "endMonth":this.state.endMonth,
            "endDay":this.state.endDay
        };
        axios.post('',time)
            .then(res=>{
                const array=JSON.parse(res.data);
                this.setState({array});
            })
    }
    render(){
        return(<div>
            <Datepicker handlerSelectorChange={this.handlerSelectorChange} state={this.state}onSubmit={this.handlerSubmit}/>
            <Table array={this.state.array}/>
        </div>);
    }
}
export default Container;
