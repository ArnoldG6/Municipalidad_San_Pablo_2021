import React, { Component } from 'react';
import './PlansTable.css';
import { Table, Accordion } from "react-bootstrap";
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

    render() {
        return (
            <div>
                {/* Mobile */}
                <div className='d-lg-none mt-2'>
                    <Accordion flush>
                        {(typeof this.props.planes === 'undefined' || this.props.planes === null) ? <h1>No se han agregado planes</h1> :
                            this.props.planes.length === 0 ? <h1>No se han agregado planes</h1> :
                                this.props.planes.map((plan) => {
                                    let statusClass = "";
                                    switch (plan.status) {
                                        case 'Activo':
                                            statusClass = 'in-progress bubble';
                                            break;
                                        case 'Inactivo':
                                            statusClass = 'no-progress bubble';
                                            break;
                                        case 'Completo':
                                            statusClass = 'completed bubble';
                                            break;
                                        default:
                                            statusClass = 'unknown bubble';
                                            break;
                                    }
                                    return (
                                        <Accordion.Item eventKey={plan.id}>
                                            <Accordion.Header>
                                                {plan.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <div className={statusClass}>
                                                    {plan.status}
                                                </div>
                                                <p>
                                                    <br />
                                                    ID: {plan.id} <br />
                                                    Fecha de Ingreso: {plan.entryDate} <br />
                                                    Autor: {plan.authorName} <br />
                                                    Tipo: {plan.type} <br />
                                                </p>
                                                <Link to={{ pathname: "/plan", search: `?id=${plan.id}` }}>+ Más información</Link>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })
                        }
                    </Accordion>
                </div>
                {/* PC */}
                <div className='d-none d-lg-block'>
                    <Table hover>
                        <thead>
                            <tr>
                                <th><button className='header-button' id="idButton" onClick={() => { this.handleSort('pk_id') }}>ID</button></th>
                                <th><button className='header-button' id="nameButton" onClick={() => { this.handleSort('name') }}> Nombre</button></th>
                                <th><button className='header-button' id="dateButton" onClick={() => { this.handleSort('entryDate') }}>Fecha de Ingreso</button></th>
                                <th><button className='header-button' id="stateButton" onClick={() => { this.handleSort('status') }}>Estado</button></th>
                                <th><button className='header-button' id="authorButton" onClick={() => { this.handleSort('authorName') }}>Autor</button></th>
                                <th><button className='header-button' id="typeButton" onClick={() => { this.handleSort('type') }}>Tipo</button></th>
                            </tr>
                        </thead>
                        {(typeof this.props.planes === 'undefined' || this.props.planes === null) ? <h1>No se han agregado planes</h1> :
                            this.props.planes.length === 0 ? <h1>No se han agregado planes</h1> :
                                <tbody>
                                    {this.props.planes.map((plan) => {
                                        let statusClass = "";
                                        switch (plan.status) {
                                            case 'Activo':
                                                statusClass = 'in-progress bubble';
                                                break;
                                            case 'Inactivo':
                                                statusClass = 'no-progress bubble';
                                                break;
                                            case 'Completo':
                                                statusClass = 'completed bubble';
                                                break;
                                            default:
                                                statusClass = 'unknown bubble';
                                                break;
                                        }
                                        return (
                                            <tr key={plan.id}>
                                                <td>{plan.id}</td>
                                                <td className="nameSlot"><Link to={{ pathname: "/plan", search: `?id=${plan.id}` }}>{plan.name}</Link></td>
                                                <td>{plan.entryDate}</td>
                                                <td><div className={statusClass}>{plan.status}</div></td>
                                                <td>{plan.authorName}</td>
                                                <td>{plan.type}</td>
                                            </tr>
                                        )
                                    })}
                                </tbody>
                        }
                    </Table>
                </div>
            </div>
        );
    }
};
export default PlansTable;
