import React, { Component } from 'react';
import './PlansTable.css';
import { Table, } from "react-bootstrap";

class PlansTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sortingWay: '',
            value: ''
        };

      

    }

    handleSort(parameter) {

        if (parameter === this.state.value) {
            this.setState({ sortingWay: !this.state.sortingWay });
        }
        else {
            this.setState({ sortingWay: true, value: parameter });
        }
        this.props.updatePlanesSort(this.state.value, this.state.sortingWay);
    }

    /* {this.props.updatePlanesSort('name',true)} */

    render() {
        return (
            <Table hover>
                <thead>
                    <tr>
                        <th><button className='header-button' id="nameButton" onClick={()=>{this.handleSort('name')}}> Nombre</button></th>
                        <th><button className='header-button' id="idButton" onClick={()=>{this.handleSort('id')}}>ID</button></th>
                        <th><button className='header-button' id="dateButton" onClick={()=>{this.handleSort('date')}}>Fecha</button></th>
                        <th><button className='header-button' id="stateButton" onClick={()=>{this.handleSort('state')}}>Estado</button></th>
                        <th><button className='header-button' id="authorButton"  onClick={()=>{this.handleSort('author')}}>Autor</button></th>
                        <th><button className='header-button' id="typeButton" onClick={()=>{this.handleSort('type')}}>Tipo</button></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><a href='/'>Poliza de chet prueba</a></td>
                        <td>2021-abc12345</td>
                        <td>13/10/2021</td>
                        <td><div className="accepted">Aceptado</div></td>
                        <td >Ricardo Milos</td>
                        <td >Proyecto</td>
                    </tr>

                    {this.props.planes.map((plan) => {
                        return (
                            <tr key={plan.id}>
                                <td><a href='/'>{plan.name}</a></td>
                                <td>{plan.id}</td>
                                <td>{plan.entryDate}</td>
                                <td><div className="accepted">{plan.status}</div></td>
                                <td>{plan.authorName}</td>
                                <td>{plan.type}</td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
        );
    }
};
export default PlansTable;
