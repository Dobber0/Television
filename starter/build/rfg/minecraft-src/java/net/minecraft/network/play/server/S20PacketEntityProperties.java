package net.minecraft.network.play.server;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S20PacketEntityProperties extends Packet
{
    private int field_149445_a;
    private final List field_149444_b = new ArrayList();
    private static final String __OBFID = "CL_00001341";

    public S20PacketEntityProperties() {}

    public S20PacketEntityProperties(int p_i45236_1_, Collection<net.minecraft.entity.ai.attributes.IAttributeInstance> p_i45236_2_)
    {
        this.field_149445_a = p_i45236_1_;
        Iterator iterator = p_i45236_2_.iterator();

        while (iterator.hasNext())
        {
            IAttributeInstance iattributeinstance = (IAttributeInstance)iterator.next();
            this.field_149444_b.add(new S20PacketEntityProperties.Snapshot(iattributeinstance.getAttribute().getAttributeUnlocalizedName(), iattributeinstance.getBaseValue(), iattributeinstance.func_111122_c()));
        }
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer data) throws IOException
    {
        this.field_149445_a = data.readInt();
        int i = data.readInt();

        for (int j = 0; j < i; ++j)
        {
            String s = data.readStringFromBuffer(64);
            double d0 = data.readDouble();
            ArrayList arraylist = new ArrayList();
            short short1 = data.readShort();

            for (int k = 0; k < short1; ++k)
            {
                UUID uuid = new UUID(data.readLong(), data.readLong());
                arraylist.add(new AttributeModifier(uuid, "Unknown synced attribute modifier", data.readDouble(), data.readByte()));
            }

            this.field_149444_b.add(new S20PacketEntityProperties.Snapshot(s, d0, arraylist));
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer data) throws IOException
    {
        data.writeInt(this.field_149445_a);
        data.writeInt(this.field_149444_b.size());
        Iterator iterator = this.field_149444_b.iterator();

        while (iterator.hasNext())
        {
            S20PacketEntityProperties.Snapshot snapshot = (S20PacketEntityProperties.Snapshot)iterator.next();
            data.writeStringToBuffer(snapshot.func_151409_a());
            data.writeDouble(snapshot.func_151410_b());
            data.writeShort(snapshot.func_151408_c().size());
            Iterator iterator1 = snapshot.func_151408_c().iterator();

            while (iterator1.hasNext())
            {
                AttributeModifier attributemodifier = (AttributeModifier)iterator1.next();
                data.writeLong(attributemodifier.getID().getMostSignificantBits());
                data.writeLong(attributemodifier.getID().getLeastSignificantBits());
                data.writeDouble(attributemodifier.getAmount());
                data.writeByte(attributemodifier.getOperation());
            }
        }
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayClient handler)
    {
        handler.handleEntityProperties(this);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandler handler)
    {
        this.processPacket((INetHandlerPlayClient)handler);
    }

    @SideOnly(Side.CLIENT)
    public int func_149442_c()
    {
        return this.field_149445_a;
    }

    @SideOnly(Side.CLIENT)
    public List<net.minecraft.network.play.server.S20PacketEntityProperties.Snapshot> func_149441_d()
    {
        return this.field_149444_b;
    }

    public class Snapshot
    {
        private final String field_151412_b;
        private final double field_151413_c;
        private final Collection field_151411_d;
        private static final String __OBFID = "CL_00001342";

        public Snapshot(String p_i45235_2_, double p_i45235_3_, Collection<net.minecraft.entity.ai.attributes.AttributeModifier> p_i45235_5_)
        {
            this.field_151412_b = p_i45235_2_;
            this.field_151413_c = p_i45235_3_;
            this.field_151411_d = p_i45235_5_;
        }

        public String func_151409_a()
        {
            return this.field_151412_b;
        }

        public double func_151410_b()
        {
            return this.field_151413_c;
        }

        public Collection<net.minecraft.entity.ai.attributes.AttributeModifier> func_151408_c()
        {
            return this.field_151411_d;
        }
    }
}