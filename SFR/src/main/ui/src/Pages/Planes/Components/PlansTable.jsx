import React, { Component } from 'react';
import './PlansTable.css';
import { Table, Accordion } from "react-bootstrap";
import { Link } from 'react-router-dom';

class PlansTable extends Component {
    render() {
        return (
            <div className='mt-2'>
                {/* Mobile */}
                <div className='d-lg-none'>
                    <Accordion>
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
                                            <Accordion.Header >
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
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Fecha de Ingreso</th>
                                <th>Estado</th>
                                <th>Autor</th>
                                <th>Tipo</th>
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
                                                <td className="nameSlot"><Link  to={{ pathname: "/plan", search: `?id=${plan.id}` }}><p className='nameText'>{plan.name}</p></Link></td>
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
