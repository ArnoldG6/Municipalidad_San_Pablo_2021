import React, { Component } from 'react';
import './PlansTable.css';
import { Table } from "react-bootstrap";

class PlansTable extends Component {
    render() {
        return (
            <Table hover>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                        <th>Autor</th>
                        <th>Tipo</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><a href='/'>Poliza de chet prueba</a></td>
                        <td>2021-abc12345</td>
                        <td>13/10/2021</td>
                        <td><div class="accepted">Aceptado</div></td>
                        <td >Ricardo Milos</td>
                        <td >Proyecto</td>
                    </tr>

                    {this.props.planes.map((plan) => {
                        return (
                            <tr key={plan.id}>
                                <td><a href='/'>{plan.name}</a></td>
                                <td>{plan.id}</td>
                                <td>{plan.entryDate}</td>
                                <td><div class="accepted">{plan.status}</div></td>
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
