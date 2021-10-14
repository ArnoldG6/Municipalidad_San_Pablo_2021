import React, { Component } from 'react';
import './PlansTable.css';
import { Table, } from "react-bootstrap";
import { Link } from 'react-router-dom';

class PlansTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sortingWay: 'desc',
            value: 'date'
        };



    }

    handleSort(parameter) {
        let sort = this.state.sortingWay;
        if (parameter === this.state.value) {
            if (sort === 'desc') {
                sort = 'asc';
            } else {
                sort = 'desc';
            }
        }
        else {
            sort = 'desc';
        }
        this.setState(
            {
                sortingWay: sort,
                value: parameter
            }, () => {
                this.props.updatePlanesSort(parameter, sort);
            }
        );
    }

    /* {this.props.updatePlanesSort('name',true)} */

    render() {
        return (
            <Table hover>
                <thead>
                    <tr>
                        <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('pk_id') }}>ID</button></th>
                        <th><button className='header-button' id="nameButton" onClick={() => { this.handleSort('name') }}> Nombre</button></th>                        
                        <th><button className='header-button' id="dateButton" onClick={() => { this.handleSort('entryDate') }}>Fecha</button></th>
                        <th><button className='header-button' id="stateButton" onClick={() => { this.handleSort('status') }}>Estado</button></th>
                        <th><button className='header-button' id="authorButton" onClick={() => { this.handleSort('authorName') }}>Autor</button></th>
                        <th><button className='header-button' id="typeButton" onClick={() => { this.handleSort('type') }}>Tipo</button></th>
                    </tr>
                </thead>
                <tbody>
                   


                    {this.props.planes.map((plan) => {
                        return (
                            <tr key={plan.id}>
                                <td><Link to={{ pathname: "/plan", search: `?id=${plan.id}` }}>{plan.name}</Link></td>
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
/*
datos quemados:

                    <tr>
                        <td>2021-abc12345</td>
                        <td><a href='/'>Poliza de chet prueba</a></td>                        
                        <td>13/10/2021</td>
                        <td><div class="accepted">Aceptado</div></td>
                        <td >Ricardo Milos</td>
                        <td >Proyecto</td>
                    </tr>


*/ 