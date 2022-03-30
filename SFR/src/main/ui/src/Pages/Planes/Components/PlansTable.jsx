import React, { Component } from 'react';
import './PlansTable.css';
import { Table, Accordion } from "react-bootstrap";
import { Link } from 'react-router-dom';
import ShowMoreText from "react-show-more-text";


class PlansTable extends Component {

    executeOnClick(isExpanded) {
        console.log(isExpanded);
    }

    render() {
        return (

            <div className='mt-2'>
                {/* Mobile */}
                <div className='d-lg-none'>
                    {(typeof this.props.planes === 'undefined' || this.props.planes === null) ? <h1>No se han agregado planes</h1> :
                        this.props.planes.length === 0 ? <h1>No se han agregado planes</h1> :
                            <Accordion>
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
                                        <Accordion.Item eventKey={plan.id} key={plan.id}>
                                            <Accordion.Header >

                                                <ShowMoreText
                                                    /* Default options */
                                                    lines={2}
                                                    more={<p>Mostrar más</p>}
                                                    less="Mostrar menos"
                                                    className="content-css"
                                                    anchorClass="accordion-header"
                                                    onClick={this.executeOnClick}
                                                    expanded={false}
                                                    truncatedEndingComponent={"... "}
                                                    width={0}
                                                >

                                                    <div class="mobilePlanName">{plan.name}</div>

                                                </ShowMoreText>


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
                    }
                </div>
                {/* PC */}
                <div className='d-none d-lg-block'>

                    {(typeof this.props.planes === 'undefined' || this.props.planes === null) ? <h1>No se han agregado planes</h1> :
                        this.props.planes.length === 0 ? <h1>No se han agregado planes</h1> :
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
                                                <td className="nameSlot">

                                                    <Link to={{ pathname: "/plan", search: `?id=${plan.id}` }}>
                                                        <ShowMoreText
                                                            /* Default options */
                                                            lines={1}
                                                            more={<button className='seeMoreButton'><i class="bi bi-caret-down"></i></button>}
                                                            less={<button className='seeMoreButton'><i class="bi bi-caret-up"></i></button>}
                                                            className="content-css"
                                                            anchorClass="my-anchor-css-class"
                                                            onClick={this.executeOnClick}
                                                            expanded={false}
                                                            truncatedEndingComponent={"... "}
                                                            width={0}
                                                        >


                                                            <p className='nameText'>{plan.name}</p>
                                                        </ShowMoreText></Link></td>

                                                <td>{plan.entryDate}</td>
                                                <td width="250px"><div className={statusClass}>{plan.status}</div></td>
                                                <td>{plan.authorName}</td>
                                                <td>{plan.type}</td>
                                            </tr>
                                        )
                                    })}

                                </tbody>

                            </Table>
                    }
                </div>
            </div>
        );
    }
};
export default PlansTable;
