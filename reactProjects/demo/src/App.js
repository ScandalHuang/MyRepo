import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Datepicker from 'material-ui/DatePicker';
import { Toggle } from 'material-ui';
import { Card, CardHeader } from 'material-ui/Card';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn, } from 'material-ui/Table'
import FlatButton from 'material-ui/FlatButton';
import moment from 'moment';
import axios from 'axios';

function Row(props) {
  return (
    <TableRow key={props.index}>
      <TableRowColumn>{props.index}</TableRowColumn>
      <TableRowColumn>{props.record.PDATE}</TableRowColumn>
      <TableRowColumn>{props.record.PFIRST}</TableRowColumn>
      <TableRowColumn>{props.record.PLAST}</TableRowColumn>
      <TableRowColumn>{props.record.EMPWNO}</TableRowColumn>
      <TableRowColumn>{props.record.EMPCNM}</TableRowColumn>
      <TableRowColumn>{props.record.DEPNAM}</TableRowColumn>
      <TableRowColumn>{props.record.DEPTCNM}</TableRowColumn>
    </TableRow>
  );
}

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      startDate: null,
      endDate: null,
      autoOk: false,
      disableYearSelection: false,
      array: [{
        CARD_ID: "CARD_ID",
        PDATE: "PDATE",
        PFIRST: "PFIRST",
        PLAST: "PLAST",
        EMPWNO: "EMPWNO",
        EMPCNM: "EMPCNM",
        DEPNAM: "DEPNAM",
        DEPTCNM: "DEPTCNM"
      }]
    }
  }
  handleChangeStartDate = (event, date) => {
    let str = moment(date).format('YYYYMMDD');
    this.setState({
      startDate: str,
    })
  }
  handleChangeEndDate = (event, date) => {
    let str = moment(date).format('YYYYMMDD');
    this.setState({
      endDate: str,
    })
  }
  handleToggled = (e, toggled) => {
    this.setState({
      [e.target.name]: toggled,
    })
  }
  componentDidMount() {
    axios.defaults.baseURL = 'http://localhost:52536/api/Record';
    axios.get().then(res => {
      const array = JSON.parse(res.data);
      this.setState({ array });
    })
  }
  handleSubmit = (e) => {
    e.preventDefault();
    const time = {
      "startDate": this.state.startDate,
      "endDate": this.state.endDate
    }
    axios.post("", time).then(res => {
      const array = JSON.parse(res.data);
      this.setState({ array });
    })
  }
  render() {
    const rows = [];
    this.state.array.forEach((element, index) => {
      rows.push(<Row record={element} index={index} />);
    });
    return (
      <div style={{ margin: '50px' }}>
        <MuiThemeProvider>
          <div>
            <div style={{ float: 'left' }}>
              <Card style={{ padding: '30px' }}>
                <CardHeader title="检索框"
                  subtitle="选择开始/结束日期筛选" />
                <Datepicker
                  onChange={this.handleChangeStartDate}
                  floatingLabelText="startDate"
                  autoOk={this.state.autoOk}
                  disableYearSelection={this.state.disableYearSelection}
                />
                <Datepicker
                  onChange={this.handleChangeEndDate}
                  floatingLabelText="endDate"
                  autoOk={this.state.autoOk}
                  disableYearSelection={this.state.disableYearSelection}
                />
                <div>
                  <Toggle
                    name="autoOk"
                    value="autoOk"
                    label="Auto Ok"
                    toggled={this.state.autoOk}
                    onToggle={this.handleToggled} />
                  <Toggle
                    name="disableYearSelection"
                    value="disableYearSelection"
                    label="disableYearSelection"
                    toggled={this.state.disableYearSelection}
                    onToggle={this.handleToggled} />
                </div>
                <FlatButton label="确定" onClick={this.handleSubmit} style={{ marginLeft: '150px', marginTop: '30px' }} />
              </Card>
            </div>
            <div style={{ marginLeft: '50px', float: 'left', width: '70%' }}>
              <Card>
                <CardHeader title="记录列表" />
                <Table height='600px'>
                  <TableHeader displaySelectAll={false}>
                    <TableRow>
                      <TableHeaderColumn>CARD_ID</TableHeaderColumn>
                      <TableHeaderColumn>PDATE</TableHeaderColumn>
                      <TableHeaderColumn>PFIRST</TableHeaderColumn>
                      <TableHeaderColumn>PLAST</TableHeaderColumn>
                      <TableHeaderColumn>EMPWNO</TableHeaderColumn>
                      <TableHeaderColumn>EMPCNM</TableHeaderColumn>
                      <TableHeaderColumn>DEPNAM</TableHeaderColumn>
                      <TableHeaderColumn>DEPTCNM</TableHeaderColumn>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {rows}
                  </TableBody>
                </Table>
              </Card>
            </div>
          </div>
        </MuiThemeProvider>
      </div>
    );
  }
}

export default App;