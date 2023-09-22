/* 
    author  :R. Gabriel
    updated :09/21/23
 */

import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.MarkupBuilder
import java.nio.charset.StandardCharsets

Message processData(Message message) {
    //  Declarando classes e variáveis
    def writer      = new StringWriter()
    def builder     = new MarkupBuilder(writer)
    def properties  = message.getProperties()

    //  Declarando XML de saída
    builder.A_Supplier {
        A_SupplierType {
            Supplier(properties.a_businessPartner)
            SupplierCrtn(properties.a_supplierCrtn)
        }
    }

    message.setBody(writer.toString())
    return message
}

//  Método para tratamento de caracteres especiais
def unescapeUnicode(def inp) {
    (inp =~ /\\u([0-9a-f]{2})([0-9a-f]{2})/).each { m ->
        def uniAsString = new String([
            Integer.parseInt(m[1], 16),
            Integer.parseInt(m[2], 16)
        ] as byte[], StandardCharsets.UTF_16)
        inp = inp.replace(m[0], uniAsString)
    }
    return inp
}
