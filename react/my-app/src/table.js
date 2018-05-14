import React from 'react';
function Row(props) {
    return(
        <tr>
            <td>{props.record.CARD_ID}</td>
            <td>{props.record.PDATE}</td>
            <td>{props.record.PFIRST}</td>
            <td>{props.record.PLAST}</td>
            <td>{props.record.EMPWNO}</td>
            <td>{props.record.EMPCNM}</td>
            <td>{props.record.DEPNAM}</td>
            <td>{props.record.DEPTCNM}</td>
        </tr>
    );
}
function Table(props) {
    const rows=[];
    props.array.forEach((record,index)=>{
        rows.push(<Row record={record} key={index}/>)
    })
    return(
        <table>
            <thead>
                <tr>
                    <th>CARD_ID</th>
                    <th>PDATE</th>
                    <th>PFIRST</th>
                    <th>PLAST</th>
                    <th>EMPWNO</th>
                    <th>EMPCNM</th>
                    <th>DEPNAM</th>
                    <th>DEPTCNM</th>
                </tr>
            </thead>
            <tbody>{rows}</tbody>
        </table>
    );
}
export default Table;