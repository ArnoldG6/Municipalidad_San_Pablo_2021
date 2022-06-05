import React, { Component } from 'react';
import './RisksTable.css';
import { Table, Accordion } from "react-bootstrap";
import { Link } from 'react-router-dom';
import ShowMoreText from "react-show-more-text";

class RisksTable extends Component {
    render() {
        return (
            <div className='mt-2'>
                {/* Mobile */}
                <div className='d-lg-none'>
                    <Accordion>
                        {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                            this.props.riesgos.length === 0 ? <h1>No se han encontrado riesgos</h1> :
                                this.props.riesgos.map((risk) => {
                                    return (
                                        <Accordion.Item eventKey={risk.id}>
                                            <Accordion.Header >

                                                <ShowMoreText
                                                    /* Default options */
                                                    lines={2}
                                                    more={<p>Mostrar más</p>}
                                                    less={<p>Mostrar menos</p>}
                                                    className="content-css"
                                                    anchorClass="accordion-header"
                                                    onClick={this.executeOnClick}
                                                    expanded={false}
                                                    truncatedEndingComponent={"... "}
                                                    keepNewLines={false}
                                                    width={300}
                                                >
                                                    <div className="mobileRiskName">{risk.name}</div>
                                                </ShowMoreText>


                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID: {risk.id} <br />
                                                    Tipo General: {risk.generalType} <br />
                                                    Tipo por Área: {risk.areaType} <br />
                                                    Probabilidad: {risk.probability} <br />
                                                    Impacto: {risk.impact} <br />
                                                    Magnitud: {risk.magnitude} <br />
                                                </p>
                                                <Link to={{ pathname: "/riesgo", search: `?id=${risk.id}` }}>+ Más información</Link>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })
                        }
                    </Accordion>
                </div>
                {/* PC */}
                <div className='d-none d-lg-block'>


                    {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                        this.props.riesgos.length === 0 ? <h1>No se han encontrado riesgos</h1> :
                            <Table hover>
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Tipo General</th>
                                        <th>Tipo por Área</th>
                                        <th>Probabilidad</th>
                                        <th>Impacto</th>
                                        <th>Magnitud</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {this.props.riesgos.map((risk) => {
                                        return (
                                            <tr key={risk.id}>
                                                <td>{risk.id}</td>
                                                <td className="nameSlot">
                                                    <Link to={{ pathname: "/riesgo", search: `?id=${risk.id}` }}>

                                                        <ShowMoreText
                                                            /* Default options */
                                                            lines={1}
                                                            more={<button className='seeMoreButton'><i className="bi bi-caret-down"></i></button>}
                                                            less={<button className='seeMoreButton'><i className="bi bi-caret-up"></i></button>}
                                                            className="content-css"
                                                            anchorClass="my-anchor-css-class"
                                                            onClick={this.executeOnClick}
                                                            expanded={false}
                                                            truncatedEndingComponent={" ... "}
                                                            width={0}
                                                        >

                                                            <p className='nameText'>{risk.name}</p>
                                                        </ShowMoreText>



                                                    </Link></td>
                                                <td>{risk.generalType}</td>
                                                <td>{risk.areaType}</td>
                                                <td>{risk.probability}</td>
                                                <td>{risk.impact}</td>
                                                <td>{risk.magnitude}</td>
                                            </tr>
                                        )
                                    })}
                                </tbody>
                            </Table >
                    }

                </div>
            </div>
        );
    }
};
export default RisksTable;