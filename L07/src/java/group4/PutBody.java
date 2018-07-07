/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author kas
 */
public class PutBody {
    @XmlElement public String oldEvent;
    @XmlElement public String newEvent;
}
